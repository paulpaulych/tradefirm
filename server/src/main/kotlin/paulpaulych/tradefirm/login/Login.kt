package paulpaulych.tradefirm.login

import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import paulpaulych.tradefirm.security.TokenPayload
import paulpaulych.tradefirm.security.JwtService
import paulpaulych.tradefirm.security.UserRepository
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
class NotAuthenticatedException: RuntimeException()

@RestController
@RequestMapping("/login")
class LoginController(
        private val passwordEncoder: PasswordEncoder,
        private val jwtService: JwtService,
        private val userRepository: UserRepository
) {

    @PostMapping
    fun login(@RequestBody req: LoginRequest): Mono<LoginResponse> =
        userRepository.findByUsername(req.username)
                .switchIfEmpty { Mono.error(NotAuthenticatedException()) }
                .map {
                    val matches = passwordEncoder.matches(req.password, it.password)
                    if (!matches) throw NotAuthenticatedException()
                    LoginResponse(
                            jwtService.generateToken(TokenPayload(it.username)),
                            it.username,
                            it.authorities.first().authority
                    )
                }

}

data class LoginRequest(
        val username: String,
        val password: String
)

data class LoginResponse(
        val token: String,
        val username: String,
        val role: String
)