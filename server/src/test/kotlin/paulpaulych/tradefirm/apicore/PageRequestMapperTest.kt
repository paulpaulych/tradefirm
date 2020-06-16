package paulpaulych.tradefirm.apicore

import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec
import org.junit.jupiter.api.Order
import paulpaulych.tradefirm.product.Product
import simpleorm.core.pagination.PageRequest
import simpleorm.core.pagination.Sort

internal class PageRequestMapperTest: FunSpec(){

    init {

        val mapper = PageRequestMapper()

        PageInfo(1)

        test("asc"){
            val dto = PageRequestDTO(0, 0,
                    listOf(SortDTO("id", SortDTO.Order.ASC)))
            val res = mapper.getPageRequest(Product::class, dto)
            res.pageNumber shouldBe 0
            res.pageSize shouldBe 0
            res.sorts[0].kProperty shouldBe Product::id
            res.sorts[0].order shouldBe Sort.Order.ASC
        }

        test("desc"){
            val dto = PageRequestDTO(0, 0,
                    listOf(SortDTO("id", SortDTO.Order.DESC)))
            val res = mapper.getPageRequest(Product::class, dto)
            res.pageNumber shouldBe 0
            res.pageSize shouldBe 0
            res.sorts[0].kProperty shouldBe Product::id
            res.sorts[0].order shouldBe Sort.Order.DESC
        }

    }

}