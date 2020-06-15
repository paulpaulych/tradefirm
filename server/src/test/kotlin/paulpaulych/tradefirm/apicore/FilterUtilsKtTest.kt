package paulpaulych.tradefirm.apicore

import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec
import paulpaulych.tradefirm.product.Product
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.filter.AndFilter
import simpleorm.core.filter.EqFilter
import simpleorm.core.filter.OrFilter
import java.math.BigDecimal

internal class FilterUtilsKtTest: FunSpec(){

    private val log by LoggerDelegate()

    init {
        val eqStringFilter = FilterDTO(
                FilterDTO.Type.STRING,
                FilterDTO.Op.EQUALS,
                listOf("1"),
                "name")
        val eqNumberFilter = FilterDTO(
                FilterDTO.Type.NUMBER,
                FilterDTO.Op.EQUALS,
                listOf("1"),
                "id")
        test("eq number filter"){

            val output = toFetchFilter(Product::class, eqNumberFilter) as EqFilter
            checkNumberFilter(output)
        }

        test("eq string filter"){
            val output = toFetchFilter(Product::class, eqStringFilter) as EqFilter
            checkStringFilter(output)
        }

        test("and filter"){
            val input = FilterDTO(
                    type = FilterDTO.Type.STRUCTURAL,
                    op = FilterDTO.Op.AND,
                    left = eqStringFilter,
                    right = eqNumberFilter)
            val output = toFetchFilter(Product::class, input) as AndFilter
            checkStringFilter(output.left as EqFilter)
            checkNumberFilter(output.right as EqFilter)
        }

        test("or filter"){
            val input = FilterDTO(
                    type = FilterDTO.Type.STRUCTURAL,
                    op = FilterDTO.Op.OR,
                    left = eqStringFilter,
                    right = eqNumberFilter)
            val output = toFetchFilter(Product::class, input) as OrFilter
            checkStringFilter(output.left as EqFilter)
            checkNumberFilter(output.right as EqFilter)
        }

    }

    private fun checkNumberFilter(output: EqFilter){
        output.params shouldBe listOf(BigDecimal(1L))
        output.kProperty shouldBe Product::id
    }

    private fun checkStringFilter(output: EqFilter){
        output.params shouldBe listOf("1")
        output.kProperty shouldBe Product::name
    }

}