package paulpaulych.tradefirm.order

import paulpaulych.tradefirm.product.Product
import paulpaulych.tradefirm.salespoint.SalesPoint
import paulpaulych.utils.Open

@Open
data class OrderItem(
        val id: Long? = null,
        val order: Order,
        val product: Product,
        val salesPoint: SalesPoint,
        val count: Int
)