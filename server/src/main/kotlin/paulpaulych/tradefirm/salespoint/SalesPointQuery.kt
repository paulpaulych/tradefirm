package paulpaulych.tradefirm.salespoint

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.apicore.*
import paulpaulych.tradefirm.salespoint.j.PlainSalesPoint
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

    suspend fun salesPointsPage(filters: List<GraphQLFilter>, pageRequest: PageRequestDTO): SalesPointDTO {
        log.info("hello from ")
        val res = PlainSalesPoint::class.findBy(
                filters.map { filterMapper.getFetchFilter(PlainSalesPoint::class, it) },
                pageRequestMapper.getPageRequest(PlainSalesPoint::class, pageRequest)
        )
        return SalesPointDTO(res.values, PageInfo(res.values.size))
    }

}
