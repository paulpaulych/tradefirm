package paulpaulych.tradefirm.config.security.common

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.config.security.jwt.JwtValidator
import paulpaulych.utils.LoggerDelegate
import reactor.core.publisher.Mono


@Component
class AuthenticationManager(
        private val jwtValidator: JwtValidator,
        private val userService: UserService
) : ReactiveAuthenticationManager {

    private val log by LoggerDelegate()

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val token =  authentication.credentials.toString()
        val jwtUser = jwtValidator.validate(token)
                ?: return Mono.empty()
        val authorities = listOf(SimpleGrantedAuthority(jwtUser.role))
        return userService.findByUsername(jwtUser.userName).map {
            UsernamePasswordAuthenticationToken(it, jwtUser.userName, authorities)
        }
    }
}
