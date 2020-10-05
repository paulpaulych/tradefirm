package paulpaulych.tradefirm.dbcommon

import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec
import paulpaulych.tradefirm.admin.crudapi.CrudEntitiesRegistry
import paulpaulych.tradefirm.admin.crudapi.CrudProduct

internal class CrudEntitiesRegistryTest: FunSpec(){

    init {
        test("get class by type"){
            CrudEntitiesRegistry().getByTypeName("product") shouldBe CrudProduct::class
        }
    }

}