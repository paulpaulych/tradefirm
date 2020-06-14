package paulpaulych.tradefirm.config.security.jwt

import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import paulpaulych.utils.LoggerDelegate

@Component
class JwtValidator {

    private val log by LoggerDelegate()

    private val secret = "Graphql"

    fun validate(token: String): JwtUser? {
        var jwtUser: JwtUser? = null
        try {
            val body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .body
            val username = body.subject
            val password = body["password"] as String
            val role = body["role"] as String
            jwtUser = JwtUser(
                    username,
                    password,
                    role)
        } catch (e: Exception) {
            log.info("token validation failed" + e.message)
        }
        return jwtUser
    }
}