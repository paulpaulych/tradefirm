package paulpaulych.tradefirm.api

import org.springframework.stereotype.Service
import simpleorm.core.filter.EqFilter
import simpleorm.core.filter.FetchFilter
import simpleorm.core.filter.LikeFilter
import simpleorm.core.utils.property
import kotlin.reflect.KClass

@Service
class GraphQLFilterMapper {
    fun getFetchFilter(requestedType: KClass<*>, graphQLFilter: GraphQLFilter): FetchFilter{
        val kProperty = requestedType.property(graphQLFilter.field)
        return when(graphQLFilter.sign){
            GraphQLFilter.Sign.EQ -> {
                EqFilter(kProperty, graphQLFilter.arg)
            }GraphQLFilter.Sign.SUBSTR -> {
                LikeFilter(kProperty, "%${graphQLFilter.arg}%")
            }
        }
    }

}