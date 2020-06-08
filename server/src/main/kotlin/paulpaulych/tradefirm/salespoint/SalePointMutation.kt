package paulpaulych.tradefirm.salespoint

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.security.Authorization
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.delete
import simpleorm.core.save

@Component
class SalePointMutation: Mutation {

    private val log by LoggerDelegate()

    @Authorization("ROLE_ADMIN")
    suspend fun saveSalesPoints(values: List<PlainSalesPoint>): List<PlainSalesPoint> {
        return values.map {
            log.info("$it")
            save(it)
        }
    }

    @Authorization("ROLE_ADMIN")
    suspend fun deleteSalesPoints(ids: List<Long>): List<Long>{
        log.info("ids to delete: $ids")
        println(ids)
        ids.forEach{ PlainSalesPoint::class.delete(it)}
        return ids
    }

}
