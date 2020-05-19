package paulpaulych.tradefirm.apicore

data class PageRequestDTO(
        val pageNumber: Int,
        val pageSize: Int,
        val sorts: List<SortDTO>
)

