package paulpaulych.tradefirm.sale

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import paulpaulych.tradefirm.config.graphql.expectedError
import paulpaulych.tradefirm.salespoint.Customer
import paulpaulych.tradefirm.product.Product
import paulpaulych.tradefirm.salespoint.Seller
import paulpaulych.tradefirm.config.security.common.Authorization
import paulpaulych.tradefirm.config.security.common.MyGraphQLContext
import paulpaulych.tradefirm.config.security.common.SellerUser
import paulpaulych.tradefirm.salespoint.StorageItem
import simpleorm.core.findById
import simpleorm.core.persist
import simpleorm.core.query
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
                    ?: expectedError("Продукта $productId нет на складе")
            if(storage.count < count){
                expectedError("Недостаточно товаров на складе")
            }
            //уменьшаем кол-во продуктов на складе
            persist(storage.copy(count = storage.count - count))
        }

        //если продавец указан и существует, добавим его к покупке
        val customer = customerId?.let {
            Customer::class.findById(customerId)
        }

        //сохраняем Sale без списка товаров, получаем saleId
        //здесь save() - это из SimpleOrm
        val saved = persist(Sale(
                null,
                customer,
                salesPoint,
                seller,
                Date()))

        //теперь сохраним список продуктов
        cart.forEach{ (productId, count)->
            val product = Product::class.findById(productId)
                    ?: error("product with id: $productId not found")
            persist(CartItem(null, saved.id!!, product, count.toLong()))
        }

        return saved
    }

    private fun findStorage(salesPointId: Long, productId: Long): StorageItem?{
        return StorageItem::class.query(
                "select * from storage where sales_point_id = ? and product_id = ?",
                listOf(salesPointId, productId)
        ).firstOrNull()
    }

    private fun getSeller(context: MyGraphQLContext): Seller {
        val seller = context.securityContext!!.authentication.principal as SellerUser
        return Seller::class.findById(seller.sellerId)
                ?: error("seller not found")
    }

}

