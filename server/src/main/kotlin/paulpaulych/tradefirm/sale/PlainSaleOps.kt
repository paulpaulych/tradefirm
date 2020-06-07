package paulpaulych.tradefirm.sale

import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.expediagroup.graphql.annotations.GraphQLName
import com.expediagroup.graphql.spring.operations.Mutation
import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.apicore.GraphQLFilter
import paulpaulych.tradefirm.apicore.GraphQLFilterMapper
import paulpaulych.tradefirm.apicore.PageInfo
import paulpaulych.tradefirm.apicore.PlainQuery
import paulpaulych.tradefirm.security.Authorization
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.delete
import simpleorm.core.findAll
import simpleorm.core.findBy
import simpleorm.core.pagination.PageRequest
import simpleorm.core.save
import java.util.*
import kotlin.reflect.KClass

data class PlainSale(
        val id: Long? = null,
        val customerId: Long,
        val salesPointId: Long,
        val sellerId: Long,
        val date: Date
)

data class SalesPage (
        val values: List<PlainSale>,
        val pageInfo: PageInfo
)

@Component
class PlainSaleQuery(
        private val filterMapper: GraphQLFilterMapper
): PlainQuery<PlainSale> {

    private val log by LoggerDelegate()

    @Authorization("ROLE_ADMIN")
    fun allPlainSales(): List<PlainSale>{
        return PlainSale::class.findAll()
    }

    @Authorization("ROLE_ADMIN")
    suspend fun plainSalesPage(filter: GraphQLFilter?, pageRequest: PageRequest): SalesPage {
        log.info("filter: $filter")
        log.info("pageRequest: size: ${pageRequest.pageSize}, number: ${pageRequest.pageNumber}")
        val res = if(filter == null){
            PlainSale::class.findAll(pageRequest)
        } else {
            PlainSale::class.findBy(
                    filterMapper.getFetchFilter(PlainSale::class, filter),
                    pageRequest
            )
        }
        log.info("fetched: ${res.values}")
        return SalesPage(res.values, PageInfo(res.values.size))
    }

    @GraphQLIgnore
    override fun requestedKClass(): KClass<PlainSale> {
        return PlainSale::class
    }

}

@Component
class PlainSaleMutation: Mutation{

    @Authorization("ROLE_ADMIN")
    suspend fun savePlainSales(values: List<PlainSale>): List<PlainSale> {
        return values.map {
            save(it)
        }
    }

    @Authorization("ROLE_ADMIN")
    suspend fun deletePlainSales(ids: List<Long>): List<Long>{
        println(ids)
        ids.forEach{ PlainSale::class.delete(it)}
        return ids
    }
}