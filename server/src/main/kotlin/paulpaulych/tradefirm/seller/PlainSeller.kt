package paulpaulych.tradefirm.seller

import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.apicore.GraphQLFilter
import paulpaulych.tradefirm.apicore.PageInfo
import paulpaulych.tradefirm.apicore.PlainQuery
import paulpaulych.tradefirm.apicore.toFetchFilter
import paulpaulych.tradefirm.security.Authorization
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.delete
import simpleorm.core.findAll
import simpleorm.core.findBy
import simpleorm.core.pagination.PageRequest
import simpleorm.core.save
import java.math.BigDecimal
import kotlin.reflect.KClass

data class PlainSeller(
        val id: Long? = null,
        val name: String,
        val salesPointId: Long,
        val salary: BigDecimal
)

data class SellerPage(
        val values: List<PlainSeller>,
        val pageInfo: PageInfo
)

@Component
class PlainSellerQuery: PlainQuery<PlainSeller> {

    private val log by LoggerDelegate()

    @Authorization("ROLE_ADMIN")
    suspend fun plainSellersPage(filter: GraphQLFilter?, pageRequest: PageRequest): SellerPage {
        log.info("hello from ")
        val res = if(filter == null){
            PlainSeller::class.findAll(pageRequest)
        } else {
            PlainSeller::class.findBy(
                    toFetchFilter(PlainSeller::class, filter),
                    pageRequest
            )
        }
        return SellerPage(res.values, PageInfo(res.values.size))
    }

    @GraphQLIgnore
    override fun requestedKClass(): KClass<PlainSeller> {
        return PlainSeller::class
    }

}

@Component
class PlainSellerMutation: Mutation {

    @Authorization("ROLE_ADMIN")
    suspend fun savePlainSellers(values: List<PlainSeller>): List<PlainSeller> {
        return values.map {
            save(it)
        }
    }

    @Authorization("ROLE_ADMIN")
    suspend fun deletePlainSellers(ids: List<Long>): List<Long>{
        println(ids)
        ids.forEach{ PlainSeller::class.delete(it) }
        return ids
    }
}