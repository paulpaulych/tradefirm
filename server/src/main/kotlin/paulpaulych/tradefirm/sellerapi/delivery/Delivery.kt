package paulpaulych.tradefirm.sellerapi.delivery

import paulpaulych.tradefirm.sellerapi.order.SupplierOrder
import paulpaulych.utils.Open
import java.util.*

@Open
data class Delivery(
        val id: Long? = null,
        val supplierOrder: SupplierOrder,
        val supplier: Supplier,
        val date: Date
)
