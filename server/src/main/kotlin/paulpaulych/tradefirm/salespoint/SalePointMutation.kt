package paulpaulych.tradefirm.salespoint

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.delete
import simpleorm.core.save

@Component
class SalePointMutation: Mutation {

    private val log by LoggerDelegate()

    suspend fun saveSalesPoints(values: List<PlainSalesPoint>): List<PlainSalesPoint> {
        return values.map {
            log.info("$it")
            save(it)
        }
    }

    suspend fun deleteSalesPoints(ids: List<Long>): List<Long>{
        log.info("ids to delete: $ids")
        println(ids)
        ids.forEach{ PlainSalesPoint::class.delete(it)}
        return ids
    }

}

//data class SalesPointSaveReq(
//        val id: Long? = null,
//        val type: String,
//        val areaId: Long? = null
//)