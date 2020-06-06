package paulpaulych.tradefirm.sale

import paulpaulych.tradefirm.customer.Customer
import paulpaulych.tradefirm.salespoint.SalesPoint
import paulpaulych.tradefirm.seller.Seller
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

