package paulpaulych.tradefirm.security.login

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import paulpaulych.tradefirm.security.jwt.JwtGenerator
import paulpaulych.tradefirm.security.jwt.JwtUser
import paulpaulych.utils.LoggerDelegate
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/login")
class LoginEndpoint(
        private val passwordEncoder: PasswordEncoder,
        private val jwtGenerator: JwtGenerator
) {

    private val ADMIN_PASSWORD = "admin"
    private val USER_PASSWORD = "user"

    private val log by LoggerDelegate()

    @PostMapping
    fun login(@RequestBody user: LoginRequest): Mono<LoginResponse> {
        val admin = JwtUser(
                "admin",
                passwordEncoder.encode(ADMIN_PASSWORD),
                "ADMIN")
        if(user.username == admin.userName
                && passwordEncoder.matches(ADMIN_PASSWORD, user.password)){
            return Mono.just(LoginResponse(jwtGenerator.generate(admin)))
        }

        val regular = JwtUser(
                "user",
                passwordEncoder.encode(USER_PASSWORD),
                "USER")
        if(user.username == regular.userName
                && passwordEncoder.matches(USER_PASSWORD, user.password)){
            return Mono.just(LoginResponse(jwtGenerator.generate(regular)))
        }

        return Mono.empty()
    }
}

