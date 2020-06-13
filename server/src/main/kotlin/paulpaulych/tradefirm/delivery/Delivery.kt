package paulpaulych.tradefirm.delivery

import paulpaulych.tradefirm.order.SupplierOrder
import paulpaulych.utils.Open
import java.util.*

@Open
data class Delivery(
        val id: Long? = null,
        val supplierOrder: SupplierOrder,
        val supplier: Supplier,
        val date: Date
)
