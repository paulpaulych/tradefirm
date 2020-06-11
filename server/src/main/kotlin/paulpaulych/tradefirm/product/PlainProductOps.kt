package paulpaulych.tradefirm.product

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

data class PlainProduct(
        val id: Long? = null,
        val name: String
)

@Component
class PlainProductQuery: PlainQuery<PlainProduct> {

    private val log by LoggerDelegate()

    @Authorization("ROLE_ADMIN")
    fun allPlainProducts(): List<PlainProduct>{
        return PlainProduct::class.findAll()
    }

    @Authorization("ROLE_ADMIN")
    suspend fun plainProductsPage(filter: GraphQLFilter?, pageRequest: PageRequest): Page<PlainProduct> {
        return PlainProduct::class.findBy(
                toFetchFilter(PlainProduct::class, filter),
                pageRequest
        )
    }

    @GraphQLIgnore
    override fun requestedKClass(): KClass<PlainProduct> {
        return PlainProduct::class
    }

}

@Component
class PlainProductMutation: Mutation{

    @Authorization("ROLE_ADMIN")
    suspend fun savePlainProducts(values: List<PlainProduct>): List<PlainProduct> {
        return values.map {
            save(it)
        }
    }

    @Authorization("ROLE_ADMIN")
    suspend fun deletePlainProducts(ids: List<Long>): List<Long>{
        ids.forEach{ PlainProduct::class.delete(it)}
        return ids
    }
}