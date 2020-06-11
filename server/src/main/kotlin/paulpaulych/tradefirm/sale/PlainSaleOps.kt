package paulpaulych.tradefirm.sale

import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.expediagroup.graphql.annotations.GraphQLName
import com.expediagroup.graphql.spring.operations.Mutation
import com.expediagroup.graphql.spring.operations.Query
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
import java.util.*
import kotlin.reflect.KClass

data class PlainSale(
        val id: Long? = null,
        val customerId: Long,
        val salesPointId: Long,
        val sellerId: Long,
        val date: Date
)

@Component
class PlainSaleQuery: PlainQuery<PlainSale> {

    private val log by LoggerDelegate()

    @Authorization("ROLE_ADMIN")
    fun allPlainSales(): List<PlainSale>{
        return PlainSale::class.findAll()
    }

    @Authorization("ROLE_ADMIN")
    suspend fun plainSalesPage(filter: GraphQLFilter?, pageRequest: PageRequest): Page<PlainSale> {
        return PlainSale::class.findBy(
                    toFetchFilter(PlainSale::class, filter),
                    pageRequest)
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
        ids.forEach{ PlainSale::class.delete(it)}
        return ids
    }
}