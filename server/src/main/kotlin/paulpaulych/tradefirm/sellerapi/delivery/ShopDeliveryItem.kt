package paulpaulych.tradefirm.sellerapi.delivery

import paulpaulych.tradefirm.sellerapi.product.Product
import paulpaulych.utils.Open

@Open
data class ShopDeliveryItem (
        val id: Long? = null,
        val product: Product,
        val shopDeliveryId: Long,
        val count: Int
)