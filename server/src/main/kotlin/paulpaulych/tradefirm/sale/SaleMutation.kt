package paulpaulych.tradefirm.sale

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import paulpaulych.tradefirm.customer.Customer
import paulpaulych.tradefirm.product.Product
import paulpaulych.tradefirm.seller.Seller
import paulpaulych.tradefirm.security.Authorization
import paulpaulych.tradefirm.security.MyGraphQLContext
import paulpaulych.tradefirm.security.SellerUser
import paulpaulych.tradefirm.storage.StorageItem
import simpleorm.core.findById
import simpleorm.core.query
import simpleorm.core.save
import java.util.*

data class CartItemInput(
    val productId: Long,
    val count: Int
)

@Component
class SaleMutation: Mutation{

    @Transactional
    @Authorization("ROLE_USER")
    fun createSale(customerId: Long?, cart: List<CartItemInput>, context: MyGraphQLContext): Sale{
        val seller = getSeller(context)
        val salesPoint = seller.salesPoint
        if(cart.isEmpty()){
            error("cart cannot be empty")
        }

        cart.forEach { (productId, count) ->
            val storage = findStorage(salesPoint.id!!, productId)
                    ?: error("storage position not found for given sales point and product")
            if(storage.count < count){
                error("not enough item in storage: ${storage.count}")
            }
            //уменьшаем кол-во продуктов на складе
            save(storage.copy(count = storage.count - count))
        }

        //если продавец указан и существует, добваим его к покупке
        val customer = customerId?.let {
            Customer::class.findById(customerId)
        }

        //сохраняем Sale без списка товаров, получаем saleId
        //здесь save() - это из SimpleOrm
        val saved = save(Sale(
                null,
                customer,
                salesPoint,
                seller,
                Date()))

        //теперь сохраним список продуктов
        cart.map{ (productId, count)->
            val product = Product::class.findById(productId)
                    ?: error("product with id: $productId not found")
            CartItem(null, saved.id!!, product, count.toLong())
        }.forEach {
            save(it)
        }

        return saved
    }

    private fun findStorage(salesPointId: Long, productId: Long): StorageItem?{
        return StorageItem::class.query(
                "select * from storage where sales_point_id = ? and product_id = ?",
                listOf(salesPointId, productId)
        ).firstOrNull()
    }

    private fun getSeller(context: MyGraphQLContext): Seller{
        val seller = context.securityContext!!.authentication.principal as SellerUser
        return Seller::class.findById(seller.sellerId)
                ?: error("seller not found")
    }

}

