package paulpaulych.tradefirm.product

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.apicore.*
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.findAll
import simpleorm.core.findBy
import simpleorm.core.findById

@Component
class ProductQuery(
        private val filterMapper: GraphQLFilterMapper,
        private val pageRequestMapper: PageRequestMapper
): Query{

    private val log by LoggerDelegate()

    suspend fun products(): List<Product>{
       return Product::class.findAll()
    }

    suspend fun product(id: Long): Product{
        return Product::class.findById(id)
                ?: error("data not found")
    }

    suspend fun productPages(filters: List<GraphQLFilter>, pageRequest: PageRequestDTO): ProductDTO{
        log.info("hello from productPages")
        val res = Product::class.findBy(
                filters.map { filterMapper.getFetchFilter(Product::class, it) },
                pageRequestMapper.getPageRequest(Product::class, pageRequest)
        )
        return ProductDTO(res.values, PageInfo(res.values.size))
    }

}
