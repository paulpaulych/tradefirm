package paulpaulych.tradefirm.config.graphql

import com.fasterxml.jackson.databind.ObjectMapper
import graphql.schema.DataFetchingEnvironment
import paulpaulych.tradefirm.config.security.common.AuthorizationDataFetcher
import reactor.core.publisher.Mono
import kotlin.reflect.KFunction

class MonoSupportedFunctionDataFetcher(
        target: Any?,
        fn: KFunction<*>,
        objectMapper: ObjectMapper
): AuthorizationDataFetcher(target, fn, objectMapper) {

    override fun get(environment: DataFetchingEnvironment): Any? {
        return convertResult(super.get(environment))
    }

    private fun convertResult(result: Any?): Any? =
            when (result) {
                is Mono<*> -> result.toFuture()
                else -> result
            }

}
