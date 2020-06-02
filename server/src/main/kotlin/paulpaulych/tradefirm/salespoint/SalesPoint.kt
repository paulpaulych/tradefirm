package paulpaulych.tradefirm.salespoint

import paulpaulych.tradefirm.application.Application
import paulpaulych.tradefirm.area.Area
import paulpaulych.tradefirm.sale.Sale
import paulpaulych.tradefirm.storage.StorageItem
import paulpaulych.utils.LoggerDelegate
import paulpaulych.utils.Open
import simpleorm.core.filter.EqFilter
import simpleorm.core.findBy
import simpleorm.core.query
import java.util.logging.Logger

@Open
data class SalesPoint(
        val id: Long? = null,
        val type: String,
        val area: Area?
){
    val storageItems: List<StorageItem>
        get() {
            val id = id ?: error("id is not set yet")
            return StorageItem::class.findBy(
                    EqFilter(StorageItem::salesPointId, id)
            )
        }

    val sales: List<Sale>
        get() {
            val id = id
                    ?: error("id is not set yet")
            return Sale::class.query(
                    """
                    select
                        s.id
                    from sale s
                        join sales_point sp on s.sales_point_id = sp.id
                    where sp.id = ?
                """.trimIndent(),
                    listOf(id)
            )
        }

    val applications: List<Application>
        get(){
            val id = id
                    ?: error("id is not set yet")
            val applications = Application::class.query("""
                select id from application where sales_point_id = ? order by date desc
                """.trimIndent(),
                listOf(id))
            log.info("applications: $applications")
            return applications
        }

    private val log by LoggerDelegate()

}

