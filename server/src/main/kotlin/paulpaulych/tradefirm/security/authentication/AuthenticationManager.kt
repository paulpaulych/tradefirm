package paulpaulych.tradefirm.security.authentication

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.security.*
import reactor.core.publisher.Mono


@Component
class AuthenticationManager(
        private val jwtService: JwtService,
        private val userRepository: UserRepository
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val token =  authentication.credentials.toString()

        val username = when(val res = jwtService.parsePayload(token)){
            is JwtParsingRes.Success -> res.tokenPayload.username
            is JwtParsingRes.TokenCorrupted -> return Mono.empty()
        }

        return userRepository.findByUsername(username)
                .filter { jwtService.validateToken(token) is JwtValidationRes.Success }
                .map { UserAuthentication(it as User) }
    }
}

private class UserAuthentication(
        private val user: User
):Authentication{
    override fun getName() = user.username
    override fun getAuthorities() = user.authorities
    override fun getCredentials() = null
    override fun getDetails() = null
    override fun getPrincipal() = user
    override fun isAuthenticated() = true
    override fun setAuthenticated(isAuthenticated: Boolean){}
}
