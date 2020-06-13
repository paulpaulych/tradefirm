package paulpaulych.tradefirm.apicore

import paulpaulych.tradefirm.config.graphql.badInputError
import simpleorm.core.filter.*
import simpleorm.core.utils.property
import java.lang.NumberFormatException
import java.math.BigDecimal
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

fun toFetchFilter(requestedType: KClass<*>, filterDTO: FilterDTO?): FetchFilter?{
    if(filterDTO == null){
        return null
    }
    return when(filterDTO.type){
        FilterDTO.Type.STRING ->{
            val property = requestedType.property(filterDTO.field!!)
            toStringFilter(property, filterDTO)
        }
        FilterDTO.Type.NUMBER ->{
            val property = requestedType.property(filterDTO.field!!)
            toBigDecimalFilter(property, filterDTO)
        }
        FilterDTO.Type.STRUCTURAL -> toStructuralFilter(requestedType, filterDTO)
    }
}

private fun toStructuralFilter(requestedType: KClass<*>, filterDTO: FilterDTO): FetchFilter{
    return when(filterDTO.op){
        FilterDTO.Op.AND -> AndFilter(
                toFetchFilter(requestedType, filterDTO.left!!)
                        ?: badInputError("AND filter left-side operand must be present"),
                toFetchFilter(requestedType, filterDTO.right!!)
                        ?: badInputError("AND filter left-side operand must be present")
        )
        FilterDTO.Op.OR -> OrFilter(
                toFetchFilter(requestedType, filterDTO.left!!)
                        ?: badInputError("OR filter left-side operand must be present"),
                toFetchFilter(requestedType, filterDTO.right!!)
                        ?: badInputError("OR filter left-side operand must be present")
        )
        else -> throwNotSupported(filterDTO.op, filterDTO.type)
    }
}


private fun toStringFilter(kProperty: KProperty1<*,*>, filterDTO: FilterDTO): FetchFilter{
    val operands = filterDTO.operands
    return when(filterDTO.op){
        FilterDTO.Op.EQUALS -> EqFilter(kProperty, operands[0])
        FilterDTO.Op.NOT_EQUALS -> NotEqFilter(kProperty, operands[0])
        FilterDTO.Op.LESS -> LessFilter(kProperty, operands[0])
        FilterDTO.Op.GREATER -> GreaterFilter(kProperty, operands[0])
        FilterDTO.Op.LESS_EQUALS -> LessEqFilter(kProperty, operands[0])
        FilterDTO.Op.GREATER_EQUALS -> GreaterEqFilter(kProperty, operands[0])
        FilterDTO.Op.STARTS_WITH -> LikeFilter(kProperty, "${operands[0]}%")
        FilterDTO.Op.ENDS_WITH -> LikeFilter(kProperty, "%${operands[0]}")
        FilterDTO.Op.CONTAINS -> LikeFilter(kProperty, "%${operands[0]}%")
        FilterDTO.Op.NOT_CONTAINS -> NotLikeFilter(kProperty, "%${operands[0]}%")
        else -> throwNotSupported(filterDTO.op, filterDTO.type)
    }
}

private fun bigDecimal(value: String): BigDecimal{
    try {
        return BigDecimal(value)
    }catch (e: NumberFormatException){
        badInputError("cannot parse filter operand as number")
    }
}

private fun toBigDecimalFilter(kProperty: KProperty1<*,*>, filterDTO: FilterDTO): FetchFilter{
    val operands = filterDTO.operands
    return when(filterDTO.op){
        FilterDTO.Op.EQUALS -> EqFilter(kProperty, bigDecimal(operands[0]))
        FilterDTO.Op.NOT_EQUALS -> NotEqFilter(kProperty, bigDecimal(operands[0]))
        FilterDTO.Op.LESS -> LessFilter(kProperty, bigDecimal(operands[0]))
        FilterDTO.Op.GREATER -> GreaterFilter(kProperty, bigDecimal(operands[0]))
        FilterDTO.Op.LESS_EQUALS -> LessEqFilter(kProperty, bigDecimal(operands[0]))
        FilterDTO.Op.GREATER_EQUALS -> GreaterEqFilter(kProperty, bigDecimal(operands[0]))
        else -> throwNotSupported(FilterDTO.Op.STARTS_WITH, FilterDTO.Type.STRING)
    }
}

private fun throwNotSupported(operator: FilterDTO.Op, type: FilterDTO.Type): Nothing{
    badInputError("operator `${operator.name}` is not applied to filter `${type}`")
}