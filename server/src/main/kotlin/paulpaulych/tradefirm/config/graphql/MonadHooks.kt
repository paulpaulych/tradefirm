package paulpaulych.tradefirm.config.graphql

import com.expediagroup.graphql.execution.FunctionDataFetcher
import com.expediagroup.graphql.execution.SimpleKotlinDataFetcherFactoryProvider
import com.expediagroup.graphql.hooks.SchemaGeneratorHooks
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.schema.DataFetcherFactory
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import kotlin.reflect.KFunction
import kotlin.reflect.KType

@Component
class MonadHooks : SchemaGeneratorHooks {
    override fun willResolveMonad(type: KType): KType = when (type.classifier) {
        Mono::class -> type.arguments.firstOrNull()?.type
        Flux::class -> type.arguments.firstOrNull() ?.type
        else -> type
    } ?: type
}

class CustomFunctionDataFetcher(
        target: Any?,
        fn: KFunction<*>,
        objectMapper: ObjectMapper) : FunctionDataFetcher(target, fn, objectMapper) {
    override fun get(environment: DataFetchingEnvironment): Any? = when (val result = super.get(environment)) {
        is Mono<*> -> result.toFuture()
        else -> result
    }
}

@Component
class CustomDataFetcherFactoryProvider(
        private val objectMapper: ObjectMapper
) : SimpleKotlinDataFetcherFactoryProvider(objectMapper) {

    override fun functionDataFetcherFactory(target: Any?, kFunction: KFunction<*>): DataFetcherFactory<Any?> = DataFetcherFactory<Any?> {
        CustomFunctionDataFetcher(
                target = target,
                fn = kFunction,
                objectMapper = objectMapper)
    }
}