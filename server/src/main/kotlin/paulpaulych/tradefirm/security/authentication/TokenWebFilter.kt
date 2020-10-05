package paulpaulych.tradefirm.security.authentication

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import paulpaulych.utils.LoggerDelegate
import reactor.core.publisher.Mono

private const val TOKEN_PREFIX = "Bearer "

@Component
class TokenWebFilter(
        private val authenticationManager: ReactiveAuthenticationManager
) : ServerSecurityContextRepository {

    private val log by LoggerDelegate()

    override fun save(swe: ServerWebExchange, sc: SecurityContext): Mono<Void>? {
        throw UnsupportedOperationException("Not supported yet.")
    }

    override fun load(swe: ServerWebExchange): Mono<SecurityContext> {
        val authHeader = swe.request.headers.getFirst(HttpHeaders.AUTHORIZATION)
                ?: return Mono.empty()
        if (!authHeader.startsWith(TOKEN_PREFIX)) {
            log.warn("coldn't find bearer string, will ignore the header.")
            return Mono.empty()
        }
        val token = authHeader.replace(TOKEN_PREFIX, "")
        val auth = UsernamePasswordAuthenticationToken(token, token)
        log.info("authenticated")
        return authenticationManager.authenticate(auth)
                .map {SecurityContextImpl(it)}
    }

}

class TokenAuthentication(
        val token: String
): Authentication{
    override fun getName() = null
    override fun getAuthorities() = mutableListOf<GrantedAuthority>()
    override fun getCredentials() = null
    override fun getDetails() = null
    override fun getPrincipal() = null
    override fun isAuthenticated() = false
    override fun setAuthenticated(isAuthenticated: Boolean){}
}