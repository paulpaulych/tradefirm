package paulpaulych.tradefirm.security

import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import javax.annotation.PostConstruct

@Service
class UserService(
        private val passwordEncoder: PasswordEncoder
): ReactiveUserDetailsService{
    private val data = mutableMapOf<String, User>()

    @PostConstruct
    fun init() {
        data["user"] = User(
                "user",
                passwordEncoder.encode("user"),
                true,
                listOf(Role.ROLE_USER))
        data["admin"] = User(
                "admin",
                passwordEncoder.encode("admin"),
                true,
                listOf(Role.ROLE_ADMIN))
    }

    override fun findByUsername(username: String): Mono<UserDetails> {
        data[username]
                ?.run { return Mono.just(this) }
                ?:run { return Mono.empty() }
    }
}