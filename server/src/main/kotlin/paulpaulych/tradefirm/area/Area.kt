package paulpaulych.tradefirm.area

import paulpaulych.utils.Open
import java.math.BigDecimal

@Open
data class Area(
        val id: Long? = null,
        val square: Long,
        val rentPrice: BigDecimal,
        val municipalServicesPrice: BigDecimal,
        val stallCount: Int
)