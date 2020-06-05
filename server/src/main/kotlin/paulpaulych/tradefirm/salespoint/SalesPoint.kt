package paulpaulych.tradefirm.salespoint

import paulpaulych.tradefirm.application.Application
import paulpaulych.tradefirm.area.Area
import paulpaulych.tradefirm.delivery.ShopDelivery
import paulpaulych.tradefirm.sale.Sale
import paulpaulych.tradefirm.storage.StorageItem
import paulpaulych.utils.LoggerDelegate
import paulpaulych.utils.Open
import simpleorm.core.filter.EqFilter
import simpleorm.core.findBy
import simpleorm.core.query

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

    val shopDeliveries: List<ShopDelivery>
        get(){
            val id = id
                    ?: error("id is not set yet")
            val deliveries = ShopDelivery::class.query(
                "select id from shop_delivery where sales_point_id = ? order by date desc",
                listOf(id))
            log.info("deliveries: $deliveries")
            return deliveries
        }

    private val log by LoggerDelegate()

}

