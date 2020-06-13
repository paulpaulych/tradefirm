package paulpaulych.tradefirm.salespoint

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.config.security.*
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.findAll
import simpleorm.core.findById
import simpleorm.core.query

@Component
class StorageQuery: Query{

    private val log by LoggerDelegate()

    @Authorization("ROLE_ADMIN")
    suspend fun storageById(id: Long): StorageItem {
        return StorageItem::class.findById(id)
                    ?: error("storage not found")
    }

    fun storages(): List<StorageItem>{
        return StorageItem::class.findAll()
    }

    fun storageBySalesPointAndProduct(salesPointId: Long, productId: Long): StorageItem {
        return StorageItem::class.query(
                "select * from storage where sales_point_id = ? and product_id = ?",
                listOf(salesPointId, productId)
        ).firstOrNull()
                ?: error("storage not found")
    }

}