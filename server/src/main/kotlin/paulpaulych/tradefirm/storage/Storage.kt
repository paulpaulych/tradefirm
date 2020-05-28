package paulpaulych.tradefirm.storage

import paulpaulych.tradefirm.product.Product
import paulpaulych.tradefirm.salespoint.SalesPoint2
import paulpaulych.utils.Open
import java.math.BigDecimal

@Open
data class Storage(
        val id: Long? = null,
        val salesPoint2: SalesPoint2,
        val product: Product,
        val count: Int,
        val price: BigDecimal
)
