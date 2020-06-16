package paulpaulych.tradefirm.apicore

import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec
import paulpaulych.tradefirm.product.Product
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.filter.*
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
                FilterDTO.Op.NOT_EQUALS,
                listOf("1"),
                "id")
        val lessNumberFilter = FilterDTO(
                FilterDTO.Type.NUMBER,
                FilterDTO.Op.LESS,
                listOf("1"),
                "id")
        val greaterNumberFilter = FilterDTO(
                FilterDTO.Type.NUMBER,
                FilterDTO.Op.GREATER,
                listOf("1"),
                "id")

        val lessEqNumberFilter = FilterDTO(
                FilterDTO.Type.NUMBER,
                FilterDTO.Op.LESS_EQUALS,
                listOf("1"),
                "id")

        val greaterEqNumberFilter = FilterDTO(
                FilterDTO.Type.NUMBER,
                FilterDTO.Op.GREATER_EQUALS,
                listOf("1"),
                "id")
        test("eq number filter"){
            val output = toFetchFilter(Product::class, eqNumberFilter) as NotEqFilter
            checkNumberFilter(output)
        }

        test("less number filter"){
            val output = toFetchFilter(Product::class, lessNumberFilter) as LessFilter
            checkNumberFilter(output)
        }

        test("less eq number filter"){
            val output = toFetchFilter(Product::class, lessEqNumberFilter) as LessEqFilter
            checkNumberFilter(output)
        }

        test("gr number filter"){
            val output= toFetchFilter(Product::class, greaterNumberFilter) as GreaterFilter
            checkNumberFilter(output)
        }

        test("eq string filter"){
            val output = toFetchFilter(Product::class, eqStringFilter) as EqFilter
            checkStringFilter(output)
        }

        test("gr eq filter"){
            val output = toFetchFilter(Product::class, greaterEqNumberFilter) as GreaterEqFilter
            checkNumberFilter(output)
        }

        test("and filter"){
            val input = FilterDTO(
                    type = FilterDTO.Type.STRUCTURAL,
                    op = FilterDTO.Op.AND,
                    left = eqStringFilter,
                    right = eqNumberFilter)
            val output = toFetchFilter(Product::class, input) as AndFilter
            checkStringFilter(output.left as EqFilter)
            checkNumberFilter(output.right as NotEqFilter)
        }

        test("or filter"){
            val input = FilterDTO(
                    type = FilterDTO.Type.STRUCTURAL,
                    op = FilterDTO.Op.OR,
                    left = eqStringFilter,
                    right = eqNumberFilter)
            val output = toFetchFilter(Product::class, input) as OrFilter
            checkStringFilter(output.left as EqFilter)
            checkNumberFilter(output.right as NotEqFilter)
        }

    }

    private fun checkNumberFilter(output: KPropertyFilter){
        output.params shouldBe listOf(BigDecimal(1L))
        output.kProperty shouldBe Product::id
    }

    private fun checkStringFilter(output: KPropertyFilter){
        output.params shouldBe listOf("1")
        output.kProperty shouldBe Product::name
    }

}