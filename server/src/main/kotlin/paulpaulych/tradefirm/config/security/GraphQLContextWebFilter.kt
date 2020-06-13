package paulpaulych.tradefirm.config.security

import com.expediagroup.graphql.spring.GraphQLConfigurationProperties
import com.expediagroup.graphql.spring.execution.ContextWebFilter
import com.expediagroup.graphql.spring.execution.GRAPHQL_CONTEXT_KEY
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.ReactorContext
import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.context.SecurityContext
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class GraphQLContextWebFilter(
        config: GraphQLConfigurationProperties,
        private val contextFactory: MyGraphQLContextFactory
        ): ContextWebFilter<MyGraphQLContext>(config, contextFactory) {


    @ExperimentalCoroutinesApi
    @Suppress("ForbiddenVoid")
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> =
            if (isApplicable(exchange.request.uri.path)) {
                mono {
                    getSecurityContext(coroutineContext[ReactorContext]!!)?.awaitFirstOrNull()
                    contextFactory.generateContext(getSecurityContext(coroutineContext[ReactorContext]!!)?.awaitFirstOrNull())
                }.flatMap { graphQLContext ->
                    chain.filter(exchange).subscriberContext { it.put(GRAPHQL_CONTEXT_KEY, graphQLContext) }
                }
            } else {
                chain.filter(exchange)
            }


    @ExperimentalCoroutinesApi
    private fun getSecurityContext(reactorContext: ReactorContext): Mono<SecurityContext>? {
        return reactorContext.context.getOrDefault<Mono<SecurityContext>>(SecurityContext::class.java, null)
    }
}