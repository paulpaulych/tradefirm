package paulpaulych.tradefirm.sale

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import paulpaulych.tradefirm.customer.Customer
import paulpaulych.tradefirm.employee.Employee
import paulpaulych.tradefirm.salespoint.SalesPoint
import paulpaulych.tradefirm.storage.Storage
import simpleorm.core.findById
import simpleorm.core.query
import simpleorm.core.save
import java.util.*

@Component
class SaleMutation: Mutation{

    @Transactional
    fun createSale(customerId: Long, salesPointId: Long,
                   sellerId: Long?, cartItems: List<CartItem>
    ): Sale{

        cartItems.forEach { cartItem ->
            val storage = findStorage(salesPointId, cartItem.productId)
                    ?: error("storage position not found for given sales point and product")
            if(storage.count < cartItem.count){
                error("not enough item in storage: ${storage.count}")
            }
            //уменьшаем кол-во продуктов на складе
            save(storage.copy(count = storage.count - cartItem.count))
        }

        //если продавец указан и существует, добваим его к покупке
        val employee = sellerId?.let {
            Employee::class.findById(it)
                ?: error("employee not found")
        }

        //сохраняем Sale без списка товаров, получаем saleId
        //здесь save() - это из SimpleOrm
        val saved = save(Sale(
                null,
                Customer::class.findById(customerId)
                        ?: error("customer does not exists"),
                SalesPoint::class.findById(salesPointId)
                        ?: error("sales point does not exists"),
                employee,
                Date()))

        //теперь сохраним список продуктов
        cartItems.map{ cartItem->
            SaleProduct(null, saved.id!!, cartItem.productId, cartItem.count.toLong())
        }.forEach {
            save(it)
        }

        return saved
    }

    private fun findStorage(salesPointId: Long, productId: Long): Storage?{
        return Storage::class.query(
                "select * from storage where sales_point_id = ? and product_id = ?",
                listOf(salesPointId, productId)
        ).firstOrNull()
    }

}

