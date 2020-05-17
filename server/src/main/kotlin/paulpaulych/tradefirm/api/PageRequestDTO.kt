package paulpaulych.tradefirm.api

data class PageRequestDTO(
        val pageNumber: Int,
        val pageSize: Int,
        val sorts: List<SortDTO>
)

