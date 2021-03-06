package paulpaulych.tradefirm.sellerapi.delivery

import paulpaulych.tradefirm.sellerapi.salespoint.SalesPoint
import paulpaulych.utils.Open
import java.util.Date

/**
 * поставка непосредственно в торговую точку. является частью поставки [Delivery]
 */
@Open
data class ShopDelivery (
        val id: Long? = null,
        val delivery: Delivery,
        val items: List<ShopDeliveryItem>,
        val salesPoint: SalesPoint,
        val date: Date
)

