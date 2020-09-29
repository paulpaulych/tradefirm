package paulpaulych.tradefirm.config.security.jwt

import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

class JwtTest: FunSpec(){

    init {

        test("valid"){
            val jwtUser = JwtUser(
                    "1",
                    "2",
                    "ROLE_ROLE"
            )
            val jwt = JwtGenerator().generate(jwtUser, System.currentTimeMillis() + 100000)

            JwtValidator().validate(jwt) shouldBe jwtUser
        }


        test("invalid"){
            val jwtUser = JwtUser(
                    "1",
                    "2",
                    "ROLE_ROLE"
            )
            val jwt = JwtGenerator().generate(jwtUser, System.currentTimeMillis() - 100000)

            JwtValidator().validate(jwt) shouldBe null
        }
    }

}