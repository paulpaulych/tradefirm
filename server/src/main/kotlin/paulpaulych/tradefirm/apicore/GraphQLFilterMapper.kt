package paulpaulych.tradefirm.apicore

import paulpaulych.tradefirm.config.graphql.badInputError
import simpleorm.core.filter.*
import simpleorm.core.utils.property
import java.lang.NumberFormatException
import java.math.BigDecimal
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

fun toFetchFilter(requestedType: KClass<*>, graphQLFilter: GraphQLFilter?): FetchFilter?{
    if(graphQLFilter == null){
        return null
    }
    return when(graphQLFilter.type){
        GraphQLFilter.Type.STRING ->{
            val property = requestedType.property(graphQLFilter.field!!)
            toStringFilter(property, graphQLFilter)
        }
        GraphQLFilter.Type.NUMBER ->{
            val property = requestedType.property(graphQLFilter.field!!)
            toBigDecimalFilter(property, graphQLFilter)
        }
        GraphQLFilter.Type.STRUCTURAL -> toStructuralFilter(requestedType, graphQLFilter)
    }
}

private fun toStructuralFilter(requestedType: KClass<*>, graphQLFilter: GraphQLFilter): FetchFilter{
    return when(graphQLFilter.op){
        GraphQLFilter.Op.AND -> AndFilter(
                toFetchFilter(requestedType, graphQLFilter.left!!)
                        ?: badInputError("AND filter left-side operand must be present"),
                toFetchFilter(requestedType, graphQLFilter.right!!)
                        ?: badInputError("AND filter left-side operand must be present")
        )
        GraphQLFilter.Op.OR -> OrFilter(
                toFetchFilter(requestedType, graphQLFilter.left!!)
                        ?: badInputError("OR filter left-side operand must be present"),
                toFetchFilter(requestedType, graphQLFilter.right!!)
                        ?: badInputError("OR filter left-side operand must be present")
        )
        else -> throwNotSupported(graphQLFilter.op, graphQLFilter.type)
    }
}


private fun toStringFilter(kProperty: KProperty1<*,*>, graphQLFilter: GraphQLFilter): FetchFilter{
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

private fun safeToString(value: Any): String{
    return value as? String
            ?: badInputError("filter param must be string")
}

private fun bigDecimal(value: String): BigDecimal{
    try {
        return BigDecimal(value)
    }catch (e: NumberFormatException){
        badInputError("cannot parse filter operand as number")
    }
}

private fun toBigDecimalFilter(kProperty: KProperty1<*,*>, graphQLFilter: GraphQLFilter): FetchFilter{
    val operands = graphQLFilter.operands
    return when(graphQLFilter.op){
        GraphQLFilter.Op.EQUALS -> EqFilter(kProperty, bigDecimal(operands[0]))
        GraphQLFilter.Op.NOT_EQUALS -> NotEqFilter(kProperty, bigDecimal(operands[0]))
        GraphQLFilter.Op.LESS -> LessFilter(kProperty, bigDecimal(operands[0]))
        GraphQLFilter.Op.GREATER -> GreaterFilter(kProperty, bigDecimal(operands[0]))
        GraphQLFilter.Op.LESS_EQUALS -> LessEqFilter(kProperty, bigDecimal(operands[0]))
        GraphQLFilter.Op.GREATER_EQUALS -> GreaterEqFilter(kProperty, bigDecimal(operands[0]))
        else -> throwNotSupported(GraphQLFilter.Op.STARTS_WITH, GraphQLFilter.Type.STRING)
    }
}

private fun throwNotSupported(operator: GraphQLFilter.Op, type: GraphQLFilter.Type): Nothing{
    badInputError("operator `${operator.name}` is not applied to filter `${type}`")
}