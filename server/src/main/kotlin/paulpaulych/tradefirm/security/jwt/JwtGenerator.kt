package paulpaulych.tradefirm.security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class JwtGenerator {

    fun generate(jwtUser: JwtUser, expiration: Long): String {
        return Jwts.builder()
                .setSubject(jwtUser.userName)
                .claim("password", jwtUser.password)
                .claim("role", jwtUser.role)
                .setExpiration(Date.from(Instant.ofEpochMilli(expiration)))
                .signWith(SignatureAlgorithm.HS512, "Graphql")
                .compact()
    }

}

