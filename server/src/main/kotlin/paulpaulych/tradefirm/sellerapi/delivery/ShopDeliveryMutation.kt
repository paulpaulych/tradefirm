package paulpaulych.tradefirm.sellerapi.delivery

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import paulpaulych.tradefirm.config.graphql.expectedError
import paulpaulych.tradefirm.sellerapi.product.Product
import paulpaulych.tradefirm.sellerapi.salespoint.getSalesPoint
import paulpaulych.tradefirm.security.authorization.Authorization
import paulpaulych.tradefirm.security.authorization.SecurityGraphQLContext
import simpleorm.core.filter.AndFilter
import simpleorm.core.filter.EqFilter
import simpleorm.core.findBy
import simpleorm.core.findById
import simpleorm.core.persist
import java.util.*

data class ShopDeliveryItemInput(
    val productId: Long,
    val count: Int
)

@Component
class ShopDeliveryMutation: Mutation {

    @Authorization("ROLE_SELLER")
    @Transactional
    fun createShopDelivery(deliveryId: Long, items: List<ShopDeliveryItemInput>, context: SecurityGraphQLContext): ShopDelivery {

        val delivery = Delivery::class.findById(deliveryId)
                ?: expectedError("Неверный ИД доставки")
        val salesPoint = getSalesPoint(context)

        checkIfAlreadyExists(deliveryId, salesPoint.id!!)

        val savedShopDelivery = persist(
                ShopDelivery(
                    delivery = delivery,
                    items = listOf(),
                    salesPoint = salesPoint,
                    date = Date())
        )

        items.forEach{ (productId, count) ->
            val product = Product::class.findById(productId)
                    ?: expectedError("продукт {ИД: $productId} не существует")
            persist(ShopDeliveryItem(
                shopDeliveryId = savedShopDelivery.id!!,
                product = product,
                count = count
            ))
        }

        return savedShopDelivery
    }

    private fun checkIfAlreadyExists(deliveryId: Long, salesPointId: Long){
        val res = PlainShopDelivery::class.findBy(
                AndFilter(
                        EqFilter(PlainShopDelivery::deliveryId, deliveryId),
                        EqFilter(PlainShopDelivery::salesPointId, salesPointId)
                ))
        if(res.isNotEmpty()){
            expectedError("запись по данной поставке уже существует. Измените ИД Доставки или обратитесь к администратору")
        }
    }
}