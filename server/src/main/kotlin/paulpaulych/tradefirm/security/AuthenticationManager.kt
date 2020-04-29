package paulpaulych.tradefirm.security

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.security.jwt.JwtValidator
import paulpaulych.utils.LoggerDelegate
import reactor.core.publisher.Mono


@Component
class AuthenticationManager(
        private val jwtValidator: JwtValidator
) : ReactiveAuthenticationManager {

    private val log by LoggerDelegate()

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val token =  authentication.credentials.toString()
        log.info("token: $token")
        val jwtUser = jwtValidator.validate(token)
                ?: return Mono.empty()
        log.info("jwtUser: $jwtUser")
        val authorities = listOf(SimpleGrantedAuthority(jwtUser.role))
        val auth = UsernamePasswordAuthenticationToken(jwtUser.userName, jwtUser.password, authorities)
        return Mono.just(auth)
    }
}