package paulpaulych.tradefirm.security.login

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
                .filter{
                    log.info("got: ${passwordEncoder.encode(req.password)}")
                    log.info("requred: ${it.password}")
                    passwordEncoder.matches(req.password, it.password)}
                .map {
                    LoginResponse(jwtGenerator.generate(
                                JwtUser(
                                        userName = it.username,
                                        password = it.password,
                                        role = it.authorities.first().authority
                                ), tokenExpiration), tokenExpiration)
                }
        }
}

