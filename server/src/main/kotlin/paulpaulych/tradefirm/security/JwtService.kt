package paulpaulych.tradefirm.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import kotlinx.serialization.toUtf8Bytes
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets

data class TokenPayload(
        val username: String
) {
    fun toMap() = mapOf(
        "username" to username
    )
}


@Service
class JwtService(@Value("\${jwt.secret-key}") secretKey: String) {

    private val secretKey = Keys.hmacShaKeyFor(secretKey.toUtf8Bytes())

    fun generateToken(tokenPayload: TokenPayload): String {
        return Jwts.builder().apply {
            setClaims(tokenPayload.toMap())
            signWith(secretKey)
        }.compact()
    }

    fun validateToken(token: String): JwtValidationRes {
        return try {
            val parser = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
            parser.parse(token)
            JwtValidationRes.Success
        } catch (e: ExpiredJwtException) {
            JwtValidationRes.Expired
        } catch (e: SignatureException) {
            JwtValidationRes.BadSignature
        } catch (e: Exception) {
            JwtValidationRes.GenericError(e)
        }
    }

    fun parsePayload(token: String): JwtParsingRes {
        return try {
            val bytes = Decoders.BASE64.decode(token.substring(token.indexOf('.'), token.lastIndexOf('.')))
            val jsonString = String(bytes, StandardCharsets.UTF_8)
            val mapper = ObjectMapper().apply {
                registerModule(KotlinModule())
            }
            val payload = mapper.readValue(jsonString, TokenPayload::class.java)
            JwtParsingRes.Success(payload)
        } catch (ex: Exception) {
            return JwtParsingRes.TokenCorrupted("Cannot parse token")
        }
    }
}

sealed class JwtParsingRes {
    data class Success(val tokenPayload: TokenPayload) : JwtParsingRes()
    data class TokenCorrupted(val msg: String) : JwtParsingRes()
}

sealed class JwtValidationRes {
    object Success : JwtValidationRes()
    object Expired : JwtValidationRes()
    object BadSignature : JwtValidationRes()
    data class GenericError(val exception: Exception) : JwtValidationRes()
}