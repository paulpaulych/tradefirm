package paulpaulych.tradefirm.security

import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import paulpaulych.tradefirm.sellerapi.SellerUser
import reactor.core.publisher.Mono
import javax.annotation.PostConstruct

@Service
class UserRepository(
        private val passwordEncoder: PasswordEncoder
): ReactiveUserDetailsService{
    private val users = mutableMapOf<String, User>()

    @PostConstruct
    fun init() {
        users["user"] = SellerUser(
                "user",
                passwordEncoder.encode("user"),
                true,
                listOf(Role.ROLE_SELLER),
                9)
        users["admin"] = User(
                "admin",
                passwordEncoder.encode("admin"),
                true,
                listOf(Role.ROLE_ADMIN))
    }

    override fun findByUsername(username: String): Mono<UserDetails> =
        users[username] ?.let { Mono.just(it as UserDetails) }
                ?:let { Mono.empty<UserDetails>() }
}

