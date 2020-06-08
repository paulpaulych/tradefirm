package paulpaulych.tradefirm.apicore

import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec
import paulpaulych.tradefirm.product.Product
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.filter.EqFilter
import java.math.BigDecimal


internal class GraphQLFilterMapperKtTest: FunSpec(){

    private val log by LoggerDelegate()

    init {

        test("eq filter"){
            val input = GraphQLFilter(
                    GraphQLFilter.Type.NUMBER,
                    GraphQLFilter.Op.EQUALS,
                    listOf("1"),
                    "id")
            val output = toFetchFilter(Product::class, input) as EqFilter
            output.params shouldBe listOf(BigDecimal(1L))
            output.kProperty shouldBe Product::id
        }

    }

}