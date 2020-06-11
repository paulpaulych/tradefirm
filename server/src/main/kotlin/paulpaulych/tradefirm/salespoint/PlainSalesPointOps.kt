package paulpaulych.tradefirm.salespoint

import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.apicore.*
import paulpaulych.tradefirm.security.Authorization
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.delete
import simpleorm.core.findAll
import simpleorm.core.findBy
import simpleorm.core.pagination.Page
import simpleorm.core.pagination.PageRequest
import simpleorm.core.save
import kotlin.reflect.KClass

/**
 * исп-ся для админки
 */
data class PlainSalesPoint(
        val id: Long? = null,
        val type: String,
        val areaId: Long? = null
)

@Component
class PlainSalesPointQuery: PlainQuery<PlainSalesPoint> {

    @Authorization("ROLE_ADMIN")
    suspend fun plainSalesPointsPage(filter: GraphQLFilter?, pageRequest: PageRequest): Page<PlainSalesPoint> {
        return PlainSalesPoint::class.findBy(
                toFetchFilter(PlainSalesPoint::class, filter),
                pageRequest
        )
    }

    @GraphQLIgnore
    override fun requestedKClass(): KClass<PlainSalesPoint> {
        return PlainSalesPoint::class
    }

}

@Component
class PlainSalesPointMutation: Mutation{

    @Authorization("ROLE_ADMIN")
    suspend fun savePlainSalesPoints(values: List<PlainSalesPoint>): List<PlainSalesPoint> {
        return values.map {
            save(it)
        }
    }

    @Authorization("ROLE_ADMIN")
    suspend fun deletePlainSalesPoints(ids: List<Long>): List<Long>{
        ids.forEach{ PlainSalesPoint::class.delete(it)}
        return ids
    }
}