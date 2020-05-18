package paulpaulych.tradefirm.api

import org.springframework.stereotype.Service
import simpleorm.core.filter.EqFilter
import simpleorm.core.filter.FetchFilter
import simpleorm.core.filter.LikeFilter
import simpleorm.core.utils.property
import java.math.BigDecimal
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

@Service
class GraphQLFilterMapper {

    fun getFetchFilter(requestedType: KClass<*>, graphQLFilter: GraphQLFilter): FetchFilter{
        val kProperty = requestedType.property(graphQLFilter.field)
        return when(graphQLFilter.type){
            GraphQLFilter.Type.STRING -> getStringFilter(kProperty, graphQLFilter)
            GraphQLFilter.Type.NUMBER -> getBigDecimalFilter(kProperty, graphQLFilter)
        }
    }

    private fun getStringFilter(kProperty: KProperty1<*,*>, graphQLFilter: GraphQLFilter): FetchFilter{
        val operands = graphQLFilter.operands
        return when(graphQLFilter.op){
            GraphQLFilter.Op.EQUALS -> EqFilter(kProperty, operands[0])
            GraphQLFilter.Op.NOT_EQUALS -> TODO()
            GraphQLFilter.Op.LESS -> TODO()
            GraphQLFilter.Op.MORE -> TODO()
            GraphQLFilter.Op.LESS_EQUALS -> TODO()
            GraphQLFilter.Op.MORE_EQUALS -> TODO()
            GraphQLFilter.Op.STARTS_WITH -> LikeFilter(kProperty, "${operands[0]}%")
            GraphQLFilter.Op.ENDS_WITH -> LikeFilter(kProperty, "%${operands[0]}")
            GraphQLFilter.Op.CONTAINS -> LikeFilter(kProperty, "%${operands[0]}%")
        }
    }

    private fun getBigDecimalFilter(kProperty: KProperty1<*,*>, graphQLFilter: GraphQLFilter): FetchFilter{
        val operands = graphQLFilter.operands
        return when(graphQLFilter.op){
            GraphQLFilter.Op.EQUALS -> EqFilter(kProperty, BigDecimal(operands[0]))
            GraphQLFilter.Op.NOT_EQUALS -> TODO()
            GraphQLFilter.Op.LESS -> TODO()
            GraphQLFilter.Op.MORE -> TODO()
            GraphQLFilter.Op.LESS_EQUALS -> TODO()
            GraphQLFilter.Op.MORE_EQUALS -> TODO()
            else -> throwNotSupported(GraphQLFilter.Op.STARTS_WITH, GraphQLFilter.Type.STRING)
        }
    }

    private fun throwNotSupported(operator: GraphQLFilter.Op, type: GraphQLFilter.Type): Nothing{
        error("operator `${operator.name}` is not applied to filter `${type}`")
    }

}