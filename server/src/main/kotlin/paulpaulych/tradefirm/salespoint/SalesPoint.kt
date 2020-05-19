package paulpaulych.tradefirm.salespoint

import paulpaulych.tradefirm.area.Area
import paulpaulych.utils.Open

@Open
data class SalesPoint(
        val id: Long? = null,
        val type: String,
        val area: Area?
){
    enum class Type {
        KIOSK,
        MAGAZIN,
        PALATKA,
        UNIVERMAG
    }
}