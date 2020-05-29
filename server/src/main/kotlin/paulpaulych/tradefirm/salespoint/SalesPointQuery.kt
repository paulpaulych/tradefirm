package paulpaulych.tradefirm.salespoint

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.apicore.*
import paulpaulych.tradefirm.product.Product
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.findAll
import simpleorm.core.findBy

@Component
class SalesPointQuery(
        private val filterMapper: GraphQLFilterMapper,
        private val pageRequestMapper: PageRequestMapper
): Query{

    private val log by LoggerDelegate()

    fun salesPoints(): List<PlainSalesPoint>{
        return PlainSalesPoint::class.findAll()
    }

    suspend fun salesPointsPage(filter: GraphQLFilter?, pageRequest: PageRequestDTO): SalesPointDTO {
        log.info("hello from ")
        val pr = pageRequestMapper.getPageRequest(PlainSalesPoint::class, pageRequest)
        val res = if(filter == null){
            PlainSalesPoint::class.findAll(pr)
        } else {
            PlainSalesPoint::class.findBy(
                    filterMapper.getFetchFilter(Product::class, filter),
                    pr
            )
        }
        return SalesPointDTO(res.values, PageInfo(res.values.size))
    }

}
