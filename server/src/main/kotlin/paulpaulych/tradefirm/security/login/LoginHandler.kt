package paulpaulych.tradefirm.security.login
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import paulpaulych.tradefirm.security.UserService
import paulpaulych.tradefirm.security.jwt.JwtGenerator
import paulpaulych.tradefirm.security.jwt.JwtUser
import paulpaulych.utils.LoggerDelegate
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/login")
class LoginEndpoint(
        private val passwordEncoder: PasswordEncoder,
        private val jwtGenerator: JwtGenerator,
        private val userService: UserService
) {

    private val log by LoggerDelegate()

    @PostMapping
    fun login(@RequestBody req: LoginRequest): Mono<LoginResponse> {
        val tokenExpiration = System.currentTimeMillis() + 46000000

        return userService.findByUsername(req.username)
                .map {
                    val matches = passwordEncoder.matches(req.password, it.password)
                    if (!matches) {
                        throw NotAuthenticatedException()
                    }
                    val jwtUser = JwtUser(
                            userName = it.username,
                            password = it.password,
                            role = it.authorities.first().authority
                    )
                    val token = jwtGenerator.generate(
                            jwtUser,
                            tokenExpiration)
                    LoginResponse(
                            token,
                            tokenExpiration,
                            jwtUser.userName,
                            jwtUser.role
                    )
                }
    }
}

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
class NotAuthenticatedException: RuntimeException()
