package paulpaulych.tradefirm.employee

import paulpaulych.tradefirm.salespoint.SalesPoint2
import paulpaulych.utils.Open
import java.math.BigDecimal

@Open
data class Employee(
        val id: Long? = null,
        val name: String,
        val salesPoint2: SalesPoint2,
        val salary: BigDecimal
)

data class EmployeeInput(
        val id: Long? = null,
        val name: String,
        val salesPointId: Long,
        val salary: BigDecimal
)

