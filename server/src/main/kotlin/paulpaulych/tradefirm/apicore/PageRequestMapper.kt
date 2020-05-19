package paulpaulych.tradefirm.apicore

import org.springframework.stereotype.Service
import simpleorm.core.pagination.PageRequest
import simpleorm.core.pagination.Sort
import simpleorm.core.utils.property
import kotlin.reflect.KClass

@Service
class PageRequestMapper{

    fun getPageRequest(requestedType: KClass<*>, dto: PageRequestDTO): PageRequest {
        val sorts = dto.sorts.map { Sort(requestedType.property(it.field), mapOrder(it.order)) }
        return PageRequest(dto.pageNumber, dto.pageSize, sorts)
    }

    private fun mapOrder(dto: SortDTO.Order): Sort.Order {
        return when(dto){
            SortDTO.Order.ASC -> Sort.Order.ASC
            SortDTO.Order.DESC -> Sort.Order.DESC
        }
    }


}