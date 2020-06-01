package paulpaulych.tradefirm.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

open class User(
        private val username: String,
        private val password: String,
        private val enabled: Boolean,
        private val roles: List<Role>
): UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>
        = roles
            .map { SimpleGrantedAuthority(it.name) }
            .toMutableList()

    override fun isEnabled(): Boolean = enabled
    override fun getUsername(): String = username
    override fun getPassword(): String = password

    override fun isCredentialsNonExpired(): Boolean = false
    override fun isAccountNonExpired(): Boolean = false
    override fun isAccountNonLocked(): Boolean = false

}

class SellerUser(
        username: String,
        password: String,
        enabled: Boolean,
        roles: List<Role>,
        val employeeId: Long
): User(username, password, enabled, roles)