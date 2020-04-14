package paulpaulych.tradefirm.employee

import paulpaulych.tradefirm.salespoint.SalesPoint
import paulpaulych.utils.Open

@Open
data class Employee(
        val id: Long? = null,
        val name: String,
        val salesPoint: SalesPoint,
        val salary: Float
)

