package paulpaulych.tradefirm.order

import paulpaulych.utils.Open
import java.util.Date

@Open
data class Order(
        val id: Long? = null,
        val date: Date
)


