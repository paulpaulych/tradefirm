package paulpaulych.tradefirm.apicore

data class GraphQLFilter(
        val type: Type,
        val op: Op,
        val operands: List<String>,
        val field: String? = null,
        val left: GraphQLFilter? = null,
        val right: GraphQLFilter? = null
){

    init {
        if(type == Type.STRUCTURAL){
            if(left == null || right == null){
                error("filter of structural type must have both left and right parts")
            }
        }else{
            if(field == null){
                error("filter of non-structural type must have FIELD assigned")
            }
        }
    }

    enum class Op{
        EQUALS,
        NOT_EQUALS,
        LESS,
        GREATER,
        LESS_EQUALS,
        GREATER_EQUALS,
        STARTS_WITH,
        ENDS_WITH,
        CONTAINS,
        NOT_CONTAINS,
        AND,
        OR
    }

    enum class Type{
        STRING, NUMBER, STRUCTURAL
    }
}
