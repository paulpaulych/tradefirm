package paulpaulych.tradefirm.employee

import paulpaulych.tradefirm.salespoint.SalesPoint
import paulpaulych.utils.Open
import java.math.BigDecimal

@Open
data class Employee(
        val id: Long? = null,
        val name: String,
        val salesPoint: SalesPoint,
        val salary: BigDecimal
)

data class EmployeeInput(
        val id: Long? = null,
        val name: String,
        val salesPointId: Long,
        val salary: BigDecimal
)

