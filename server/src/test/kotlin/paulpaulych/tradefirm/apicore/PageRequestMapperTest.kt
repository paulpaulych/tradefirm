package paulpaulych.tradefirm.apicore

import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec
import paulpaulych.tradefirm.admin.crudapi.PageInfoDTO
import paulpaulych.tradefirm.admin.crudapi.PageRequestDTO
import paulpaulych.tradefirm.admin.crudapi.PageRequestMapper
import paulpaulych.tradefirm.admin.crudapi.SortDTO
import paulpaulych.tradefirm.sellerapi.product.Product
import simpleorm.core.pagination.Sort

internal class PageRequestMapperTest: FunSpec(){

    init {

        val mapper = PageRequestMapper()

        PageInfoDTO(1)

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