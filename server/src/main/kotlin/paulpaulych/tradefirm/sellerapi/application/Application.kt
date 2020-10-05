package paulpaulych.tradefirm.sellerapi.application

import paulpaulych.tradefirm.sellerapi.salespoint.SalesPoint
import paulpaulych.utils.Open
import simpleorm.core.query
import java.util.*

@Open
/**
 * заявка на поставку продуктов (не приложение)
 */
data class Application (
        val id: Long? = null,
        val salesPoint: SalesPoint,
        val date: Date,
        val newFlag: Boolean? = true
){
    val items: List<ApplicationItem>
        get() {
            val id = id ?: error("id is not set yet")
            val sql = "select id from application_product where application_id = ?"
            return ApplicationItem::class.query(sql, listOf(id))
        }
}