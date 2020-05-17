package paulpaulych.tradefirm.api

data class SortDTO (
        val field: String,
        val order: Order
) {
    enum class Order { ASC, DESC }
}
