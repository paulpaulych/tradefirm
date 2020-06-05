package paulpaulych.tradefirm.delivery

import java.util.*

data class PlainShopDelivery(
        val id: Long? = null,
        val deliveryId: Long,
        val salesPointId: Long,
        val date: Date
)