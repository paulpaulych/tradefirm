package paulpaulych.tradefirm.config.security.login

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import paulpaulych.tradefirm.config.security.common.UserService
import paulpaulych.tradefirm.config.security.jwt.JwtGenerator
import paulpaulych.tradefirm.config.security.jwt.JwtUser
import paulpaulych.utils.LoggerDelegate
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@RestController
@RequestMapping("/login")
class LoginController(
        private val passwordEncoder: PasswordEncoder,
        private val jwtGenerator: JwtGenerator,
        private val userService: UserService
) {

    private val log by LoggerDelegate()

    @PostMapping
    fun login(@RequestBody req: LoginRequest): Mono<LoginResponse> {
        val tokenExpiration = System.currentTimeMillis() + 46000000

        return userService.findByUsername(req.username)
                .switchIfEmpty { Mono.error(NotAuthenticatedException()) }
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