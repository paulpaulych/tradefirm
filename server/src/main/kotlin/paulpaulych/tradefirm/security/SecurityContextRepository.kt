package paulpaulych.tradefirm.security

import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import paulpaulych.utils.LoggerDelegate
import reactor.core.publisher.Mono


@Component
class SecurityContextRepository(
        private val authenticationManager: ReactiveAuthenticationManager
) : ServerSecurityContextRepository {

    private val log by LoggerDelegate()

    private val TOKEN_PREFIX = "Bearer "

    override fun save(swe: ServerWebExchange, sc: SecurityContext): Mono<Void>? {
        throw UnsupportedOperationException("Not supported yet.")
    }

    override fun load(swe: ServerWebExchange): Mono<SecurityContext> {
        val request: ServerHttpRequest = swe.request
        val authHeader = request.headers.getFirst(HttpHeaders.AUTHORIZATION)
                ?: return Mono.empty()
        if (!authHeader.startsWith(TOKEN_PREFIX)) {
            log.warn("couldn't find bearer string, will ignore the header.")
            return Mono.empty()
        }
        val token = authHeader.replace(TOKEN_PREFIX, "")
        val auth = UsernamePasswordAuthenticationToken(token, token)
        log.info("authenticated")
        return authenticationManager.authenticate(auth)
                .map {SecurityContextImpl(it)}
    }

}