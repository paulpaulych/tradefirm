package paulpaulych.tradefirm.salespoint

import paulpaulych.tradefirm.application.Application
import paulpaulych.tradefirm.area.Area
import paulpaulych.tradefirm.delivery.ShopDelivery
import paulpaulych.tradefirm.sale.Sale
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
            return StorageItem::class.findBy(
                    EqFilter(StorageItem::salesPointId, getNotNullId())
            )
        }

    val sales: List<Sale>
        get() {
            return Sale::class.query(
                    """
                    select
                        s.id
                    from sale s
                        join sales_point sp on s.sales_point_id = sp.id
                    where sp.id = ?
                """.trimIndent(),
                    listOf(getNotNullId())
            )
        }

    val applications: List<Application>
        get(){
            val sql = "select id from application where sales_point_id = ? order by date desc"
            return  Application::class.query(sql, listOf(getNotNullId()))
        }

    val shopDeliveries: List<ShopDelivery>
        get(){
            val sql = "select id from shop_delivery where sales_point_id = ? order by date desc"
            return ShopDelivery::class.query(sql, listOf(getNotNullId()))
        }

    private val log by LoggerDelegate()

    private fun getNotNullId(): Long {
        return id ?: error("id is not set yet")
    }
}

