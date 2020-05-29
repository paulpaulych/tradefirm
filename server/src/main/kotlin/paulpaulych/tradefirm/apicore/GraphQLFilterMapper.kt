package paulpaulych.tradefirm.apicore

import org.springframework.stereotype.Service
import simpleorm.core.filter.*
import simpleorm.core.utils.property
import java.math.BigDecimal
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

@Service
class GraphQLFilterMapper {

    fun getFetchFilter(requestedType: KClass<*>, graphQLFilter: GraphQLFilter): FetchFilter{
        return when(graphQLFilter.type){
            GraphQLFilter.Type.STRING ->{
                val property = requestedType.property(graphQLFilter.field!!)
                getStringFilter(property, graphQLFilter)
            }
            GraphQLFilter.Type.NUMBER ->{
                val property = requestedType.property(graphQLFilter.field!!)
                getBigDecimalFilter(property, graphQLFilter)
            }
            GraphQLFilter.Type.STRUCTURAL -> getStructuralFilter(requestedType, graphQLFilter)
        }
    }

    fun getStructuralFilter(requestedType: KClass<*>, graphQLFilter: GraphQLFilter): FetchFilter{
        return when(graphQLFilter.op){
            GraphQLFilter.Op.AND -> AndFilter(
                    getFetchFilter(requestedType, graphQLFilter.left!!),
                    getFetchFilter(requestedType, graphQLFilter.right!!)
            )
            GraphQLFilter.Op.OR -> OrFilter(
                    getFetchFilter(requestedType, graphQLFilter.left!!),
                    getFetchFilter(requestedType, graphQLFilter.right!!)
            )
            else -> throwNotSupported(graphQLFilter.op, graphQLFilter.type)
        }
    }

    private fun getStringFilter(kProperty: KProperty1<*,*>, graphQLFilter: GraphQLFilter): FetchFilter{
        val operands = graphQLFilter.operands
        return when(graphQLFilter.op){
            GraphQLFilter.Op.EQUALS -> EqFilter(kProperty, operands[0])
            GraphQLFilter.Op.NOT_EQUALS -> NotEqFilter(kProperty, operands[0])
            GraphQLFilter.Op.LESS -> LessFilter(kProperty, operands[0])
            GraphQLFilter.Op.GREATER -> GreaterFilter(kProperty, operands[0])
            GraphQLFilter.Op.LESS_EQUALS -> LessEqFilter(kProperty, operands[0])
            GraphQLFilter.Op.GREATER_EQUALS -> GreaterEqFilter(kProperty, operands[0])
            GraphQLFilter.Op.STARTS_WITH -> LikeFilter(kProperty, "${operands[0]}%")
            GraphQLFilter.Op.ENDS_WITH -> LikeFilter(kProperty, "%${operands[0]}")
            GraphQLFilter.Op.CONTAINS -> LikeFilter(kProperty, "%${operands[0]}%")
            GraphQLFilter.Op.NOT_CONTAINS -> NotLikeFilter(kProperty, "%${operands[0]}%")
            else -> throwNotSupported(graphQLFilter.op, graphQLFilter.type)
        }
    }

    private fun getBigDecimalFilter(kProperty: KProperty1<*,*>, graphQLFilter: GraphQLFilter): FetchFilter{
        val operands = graphQLFilter.operands
        return when(graphQLFilter.op){
            GraphQLFilter.Op.EQUALS -> EqFilter(kProperty, BigDecimal(operands[0]))
            GraphQLFilter.Op.NOT_EQUALS -> NotEqFilter(kProperty, BigDecimal(operands[0]))
            GraphQLFilter.Op.LESS -> LessFilter(kProperty, BigDecimal(operands[0]))
            GraphQLFilter.Op.GREATER -> GreaterFilter(kProperty, BigDecimal(operands[0]))
            GraphQLFilter.Op.LESS_EQUALS -> LessEqFilter(kProperty, BigDecimal(operands[0]))
            GraphQLFilter.Op.GREATER_EQUALS -> GreaterEqFilter(kProperty, BigDecimal(operands[0]))
            else -> throwNotSupported(GraphQLFilter.Op.STARTS_WITH, GraphQLFilter.Type.STRING)
        }
    }

    private fun throwNotSupported(operator: GraphQLFilter.Op, type: GraphQLFilter.Type): Nothing{
        error("operator `${operator.name}` is not applied to filter `${type}`")
    }

}