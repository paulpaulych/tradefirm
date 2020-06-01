package paulpaulych.tradefirm.salespoint

import paulpaulych.tradefirm.area.Area
import paulpaulych.tradefirm.storage.StorageItem
import paulpaulych.utils.Open
import simpleorm.core.filter.EqFilter
import simpleorm.core.findBy

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
}

