package paulpaulych.tradefirm.salespoint

import com.expediagroup.graphql.annotations.GraphQLIgnore
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.apicore.*
import paulpaulych.tradefirm.sale.PlainSale
import paulpaulych.tradefirm.security.Authorization
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.findAll
import simpleorm.core.findBy
import simpleorm.core.pagination.PageRequest
import kotlin.reflect.KClass

@Component
class PlainSalesPointQuery(
        private val filterMapper: GraphQLFilterMapper,
        private val pageRequestMapper: PageRequestMapper
): PlainQuery<PlainSalesPoint>{

    private val log by LoggerDelegate()

    @Authorization("ROLE_ADMIN")
    fun salesPoints(): List<PlainSalesPoint>{
        return PlainSalesPoint::class.findAll()
    }

    @Authorization("ROLE_ADMIN")
    suspend fun salesPointsPage(filter: GraphQLFilter?, pageRequest: PageRequest): SalesPointPage {
        log.info("hello from ")
        val res = if(filter == null){
            PlainSalesPoint::class.findAll(pageRequest)
        } else {
            PlainSalesPoint::class.findBy(
                    filterMapper.getFetchFilter(PlainSalesPoint::class, filter),
                    pageRequest
            )
        }
        return SalesPointPage(res.values, PageInfo(res.values.size))
    }

    @GraphQLIgnore
    override fun requestedKClass(): KClass<PlainSalesPoint> {
        return PlainSalesPoint::class
    }

}
