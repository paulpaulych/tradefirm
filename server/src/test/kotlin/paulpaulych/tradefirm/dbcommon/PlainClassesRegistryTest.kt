package paulpaulych.tradefirm.dbcommon

import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

internal class PlainClassesRegistryTest: FunSpec(){

    init {
        test("get class by type"){
            PlainClassesRegistry().getByTypeName("product") shouldBe PlainProduct::class
        }
    }

}