package paulpaulych.tradefirm.apicore

import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec
import paulpaulych.tradefirm.product.Product
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.filter.EqFilter
import java.math.BigDecimal


internal class FilterUtilsKtTest: FunSpec(){

    private val log by LoggerDelegate()

    init {

        test("eq filter"){
            val input = FilterDTO(
                    FilterDTO.Type.NUMBER,
                    FilterDTO.Op.EQUALS,
                    listOf("1"),
                    "id")
            val output = toFetchFilter(Product::class, input) as EqFilter
            output.params shouldBe listOf(BigDecimal(1L))
            output.kProperty shouldBe Product::id
        }

    }

}