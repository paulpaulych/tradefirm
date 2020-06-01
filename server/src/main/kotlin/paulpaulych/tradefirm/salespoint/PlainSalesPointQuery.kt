package paulpaulych.tradefirm.salespoint

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.apicore.*
import paulpaulych.tradefirm.product.Product
import paulpaulych.tradefirm.security.Authorization
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.findAll
import simpleorm.core.findBy

@Component
class PlainSalesPointQuery(
        private val filterMapper: GraphQLFilterMapper,
        private val pageRequestMapper: PageRequestMapper
): Query{

    private val log by LoggerDelegate()

    @Authorization("ROLE_ADMIN")
    fun salesPoints(): List<PlainSalesPoint>{
        return PlainSalesPoint::class.findAll()
    }

    @Authorization("ROLE_ADMIN")
    suspend fun salesPointsPage(filter: GraphQLFilter?, pageRequest: PageRequestDTO): SalesPointDTO {
        log.info("hello from ")
        val pr = pageRequestMapper.getPageRequest(PlainSalesPoint::class, pageRequest)
        val res = if(filter == null){
            PlainSalesPoint::class.findAll(pr)
        } else {
            PlainSalesPoint::class.findBy(
                    filterMapper.getFetchFilter(PlainSalesPoint::class, filter),
                    pr
            )
        }
        return SalesPointDTO(res.values, PageInfo(res.values.size))
    }

}
