package paulpaulych.tradefirm.security.jwt

import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec
import junit.framework.Assert.assertTrue
import paulpaulych.tradefirm.security.JwtService
import paulpaulych.tradefirm.security.JwtParsingRes
import paulpaulych.tradefirm.security.TokenPayload
import paulpaulych.tradefirm.security.JwtValidationRes

class JwtServiceTest: FunSpec(){

    private val jwtService = JwtService("afdsfdsfdsafdsafdsafadsfdsfdsafdsafdsafdsafdsafdsafdsafdsafdsa")

    init {

        test("valid"){
            val payload = TokenPayload("1")
            val token = jwtService.generateToken(payload)
            val parsed = jwtService.parsePayload(token)
            assertTrue(parsed is JwtParsingRes.Success)
            parsed as JwtParsingRes.Success
            parsed.tokenPayload shouldBe payload
            assertTrue(jwtService.validateToken(token) is JwtValidationRes.Success)
        }


        test("bad signature"){
            val payload = TokenPayload("1")
            val token = jwtService.generateToken(payload)
            val old = token.last()
            val new = if(old == '1') '2' else '2'
            val fakeToken = token.substring(0, token.length - 1) + new
            val parsed = jwtService.parsePayload(fakeToken)
            assertTrue(parsed is JwtParsingRes.Success)
            parsed as JwtParsingRes.Success
            parsed.tokenPayload shouldBe payload
            assertTrue(jwtService.validateToken(fakeToken) is JwtValidationRes.BadSignature)
        }
    }

}