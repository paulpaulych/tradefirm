package paulpaulych.tradefirm.storage

import paulpaulych.tradefirm.product.Product
import paulpaulych.tradefirm.salespoint.SalesPoint
import paulpaulych.utils.Open
import java.math.BigDecimal

@Open
data class Storage(
        val id: Long? = null,
        val salesPoint: SalesPoint,
        val product: Product,
        val count: Int,
        val price: BigDecimal
)
