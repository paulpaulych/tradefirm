package paulpaulych.tradefirm.sellerapi.order

import paulpaulych.utils.Open
import java.util.Date

@Open
data class SupplierOrder(
        val id: Long? = null,
        val date: Date
)


