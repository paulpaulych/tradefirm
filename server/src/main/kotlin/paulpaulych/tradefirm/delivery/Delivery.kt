package paulpaulych.tradefirm.delivery

import paulpaulych.tradefirm.order.Order
import paulpaulych.tradefirm.supplier.Supplier
import paulpaulych.utils.Open
import java.util.*

@Open
data class Delivery(
        val id: Long? = null,
        val order: Order,
        val supplier: Supplier,
        val date: Date
)

