package paulpaulych.tradefirm.seller

import paulpaulych.tradefirm.salespoint.SalesPoint
import paulpaulych.utils.Open
import java.math.BigDecimal

@Open
data class Seller(
        val id: Long? = null,
        val name: String,
        val salesPoint: SalesPoint,
        val salary: BigDecimal
)

