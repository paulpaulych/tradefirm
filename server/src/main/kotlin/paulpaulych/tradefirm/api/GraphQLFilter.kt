package paulpaulych.tradefirm.api

open class GraphQLFilter(
        val type: Type,
        val field: String,
        val op: Op,
        val operands: List<String>
){
    enum class Op{
        EQUALS,
        NOT_EQUALS,
        LESS,
        MORE,
        LESS_EQUALS,
        MORE_EQUALS,
        STARTS_WITH,
        ENDS_WITH,
        CONTAINS
    }

    enum class Type{
        STRING, NUMBER
    }
}
