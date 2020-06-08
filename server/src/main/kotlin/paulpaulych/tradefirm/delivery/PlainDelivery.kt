package paulpaulych.tradefirm.delivery

import java.util.*

data class PlainDelivery (
        val id: Long? = null,
        val orderId: Long,
        val supplierId: Long,
        val date: Date
)

