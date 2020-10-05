package paulpaulych.tradefirm.admin.crudapi

import org.springframework.stereotype.Service
import simpleorm.core.pagination.PageRequest
import simpleorm.core.pagination.Sort
import simpleorm.core.utils.property
import kotlin.reflect.KClass

data class SortDTO (
        val field: String,
        val order: Order
) {
    enum class Order { ASC, DESC }
}

data class PageInfoDTO (
        val pageSize: Int
)

data class PageRequestDTO(
        val pageNumber: Int,
        val pageSize: Int,
        val sorts: List<SortDTO>
)

@Service
class PageRequestMapper{

    fun getPageRequest(requestedType: KClass<*>, dto: PageRequestDTO): PageRequest {
        val sorts = dto.sorts.map { Sort(requestedType.property(it.field), mapOrder(it.order)) }
        return PageRequest(dto.pageNumber, dto.pageSize, sorts)
    }

    private fun mapOrder(dto: SortDTO.Order): Sort.Order =
        when(dto){
            SortDTO.Order.ASC -> Sort.Order.ASC
            SortDTO.Order.DESC -> Sort.Order.DESC
        }
}