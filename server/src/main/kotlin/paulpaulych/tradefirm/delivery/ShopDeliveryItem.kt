package paulpaulych.tradefirm.delivery

import paulpaulych.tradefirm.product.Product
import paulpaulych.utils.Open

@Open
data class ShopDeliveryItem (
        val id: Long? = null,
        val product: Product,
        val shopDeliveryId: Long,
        val count: Int
)