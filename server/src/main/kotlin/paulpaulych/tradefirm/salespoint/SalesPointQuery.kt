package paulpaulych.tradefirm.salespoint

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.apicore.*
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.findAll
import simpleorm.core.findBy

@Component
class SalesPointQuery(
        private val filterMapper: GraphQLFilterMapper,
        private val pageRequestMapper: PageRequestMapper
): Query{

    private val log by LoggerDelegate()

    fun salesPoints(): List<SalesPoint>{
        return SalesPoint::class.findAll()
    }

    suspend fun salesPointsPage(filters: List<GraphQLFilter>, pageRequest: PageRequestDTO): SalesPointDTO {
        log.info("hello from ")
        val res = SalesPoint::class.findBy(
                filters.map { filterMapper.getFetchFilter(SalesPoint::class, it) },
                pageRequestMapper.getPageRequest(SalesPoint::class, pageRequest)
        )
        return SalesPointDTO(res.values, PageInfo(res.values.size))
    }

}
