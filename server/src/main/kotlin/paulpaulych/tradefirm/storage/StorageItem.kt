package paulpaulych.tradefirm.storage

import paulpaulych.tradefirm.product.Product
import paulpaulych.utils.Open
import java.math.BigDecimal

@Open
data class StorageItem(
        val id: Long? = null,
        val salesPointId: Long,
        val product: Product,
        val count: Int,
        val price: BigDecimal
)