package paulpaulych.tradefirm.sellerapi.sale

import paulpaulych.tradefirm.sellerapi.salespoint.Customer
import paulpaulych.tradefirm.sellerapi.salespoint.SalesPoint
import paulpaulych.tradefirm.sellerapi.salespoint.Seller
import paulpaulych.utils.Open
import java.util.*

@Open
data class Sale (
        val id: Long? = null,
        val customer: Customer?,
        val salesPoint: SalesPoint,
        val seller: Seller?,
        val date: Date,
        val cartItems: List<CartItem> = listOf()
)

