package paulpaulych.tradefirm.product

import com.expediagroup.graphql.annotations.GraphQLIgnore
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.apicore.*
import paulpaulych.tradefirm.security.Authorization
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.findAll
import simpleorm.core.findBy
import simpleorm.core.findById
import simpleorm.core.pagination.PageRequest
import kotlin.reflect.KClass

@Component
class ProductQuery(
        private val filterMapper: GraphQLFilterMapper
): PlainQuery<Product>{

    private val log by LoggerDelegate()

    @Authorization("ROLE_USER")
    suspend fun products(): List<Product>{
        return Product::class.findAll()
    }

    @Authorization("ROLE_ADMIN")
    suspend fun product(id: Long): Product{
        return Product::class.findById(id)
                ?: error("data not found")
    }

    @Authorization("ROLE_ADMIN")
    suspend fun productsPage(filter: GraphQLFilter?, pageRequest: PageRequest): ProductPage{
        log.info("hello from productPages")
        val res = if(filter == null){
            Product::class.findAll(pageRequest)
        }else {
            Product::class.findBy(
                    filterMapper.getFetchFilter(Product::class, filter),
                    pageRequest
            )
        }
        return ProductPage(res.values, PageInfo(res.values.size))
    }

    @GraphQLIgnore
    override fun requestedKClass(): KClass<Product> {
        return Product::class
    }

}
