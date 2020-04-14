package paulpaulych.tradefirm.storage

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.sale.SaleProduct
import simpleorm.core.findAll
import simpleorm.core.findById
import simpleorm.core.query

@Component
class StorageQuery: Query{

    fun storage(id: Long): Storage{
        return Storage::class.findById(id)
                ?: error("storage not found")
    }

    fun storages(): List<Storage>{
        return Storage::class.findAll()
    }

    fun storageBySalesPointAndProduct(salesPointId: Long, productId: Long): Storage{
        return Storage::class.query(
                "select * from storage where sales_point_id = ? and product_id = ?",
                listOf(salesPointId, productId)
        ).firstOrNull()
                ?: error("storage not found")
    }


}