package paulpaulych.tradefirm.api

class GraphQLFilter(
        val field: String,
        val sign: Sign,
        val arg: String
){
    enum class Sign{
        EQ, SUBSTR
    }
}
