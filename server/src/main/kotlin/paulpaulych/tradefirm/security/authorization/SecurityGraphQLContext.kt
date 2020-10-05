package paulpaulych.tradefirm.security.authorization

import com.expediagroup.graphql.execution.GraphQLContext
import com.expediagroup.graphql.spring.execution.GraphQLContextFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.reactor.ReactorContext
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.security.core.context.SecurityContext
import org.springframework.stereotype.Component
import kotlin.coroutines.coroutineContext

class SecurityGraphQLContext(val securityContext: SecurityContext?) : GraphQLContext

@Component
class SecurityGraphQLContextFactory: GraphQLContextFactory<SecurityGraphQLContext> {

    @ExperimentalCoroutinesApi
    override suspend fun generateContext(
            request: ServerHttpRequest,
            response: ServerHttpResponse
    ): SecurityGraphQLContext {
        val reactorContext = coroutineContext[ReactorContext]?.context
                ?: error("reactor context unavailable")
        val securityContext = reactorContext.get(SecurityContext::class.java)
                ?: error("security context is null")
        return SecurityGraphQLContext(securityContext)
    }

    suspend fun generateContext(securityContext: SecurityContext?): SecurityGraphQLContext {
        return SecurityGraphQLContext(securityContext)
    }
}

