package paulpaulych.tradefirm.delivery

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import paulpaulych.tradefirm.config.graphql.expectedError
import paulpaulych.tradefirm.product.Product
import paulpaulych.tradefirm.salespoint.getSalesPoint
import paulpaulych.tradefirm.config.security.common.Authorization
import paulpaulych.tradefirm.config.security.common.MyGraphQLContext
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

    @Authorization("ROLE_USER")
    @Transactional
    fun createShopDelivery(deliveryId: Long, items: List<ShopDeliveryItemInput>, context: MyGraphQLContext): ShopDelivery{

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
            expectedError("запись по данной поставке уже существует. Измените ИД Доставки или обратитель к администратору")
        }
    }
}