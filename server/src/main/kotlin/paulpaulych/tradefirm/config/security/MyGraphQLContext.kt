package paulpaulych.tradefirm.config.security

import com.expediagroup.graphql.execution.GraphQLContext
import com.expediagroup.graphql.spring.execution.GraphQLContextFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.reactor.ReactorContext
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.security.core.context.SecurityContext
import org.springframework.stereotype.Component
import kotlin.coroutines.coroutineContext

class MyGraphQLContext(val securityContext: SecurityContext?) : GraphQLContext

@Component
class MyGraphQLContextFactory: GraphQLContextFactory<MyGraphQLContext> {

    @ExperimentalCoroutinesApi
    override suspend fun generateContext(
            request: ServerHttpRequest,
            response: ServerHttpResponse
    ): MyGraphQLContext {
        val reactorContext = coroutineContext[ReactorContext]
                ?.context
                ?: error("reactor context unavailable")
        val securityContext = reactorContext.get(SecurityContext::class.java)
                ?: error("security context is null")
        return MyGraphQLContext(securityContext)
    }

    suspend fun generateContext(securityContext: SecurityContext?): MyGraphQLContext{
        return MyGraphQLContext(securityContext)
    }
}

