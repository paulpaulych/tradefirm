package paulpaulych.tradefirm.security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component

@Component
class JwtGenerator {

    fun generate(jwtUser: JwtUser): String {
        val claims = Jwts.claims()
                .setSubject(jwtUser.userName)
        claims["password"] = java.lang.String.valueOf(jwtUser.password)
        claims["role"] = jwtUser.role
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "Graphql")
                .compact()
    }

}

