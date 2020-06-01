package paulpaulych.tradefirm.config.graphql

import com.expediagroup.graphql.execution.SimpleKotlinDataFetcherFactoryProvider
import com.expediagroup.graphql.hooks.SchemaGeneratorHooks
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.language.StringValue
import graphql.schema.*
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.security.AuthorizationDataFetcher
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.ZonedDateTime
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KType

@Component
class MonadHooks : SchemaGeneratorHooks {
    override fun willResolveMonad(type: KType): KType = when (type.classifier) {
        Mono::class -> type.arguments.firstOrNull()?.type
        Flux::class -> type.arguments.firstOrNull() ?.type
        else -> type
    } ?: type

    override fun willGenerateGraphQLType(type: KType): GraphQLType? = when (type.classifier as? KClass<*>) {
        Date::class -> dateScalarType
        else -> null
    }

}
internal val dateScalarType = GraphQLScalarType.newScalar()
        .name("Date")
        .coercing(DateCoercing)
        .build()

class CustomFunctionDataFetcher(
        target: Any?,
        fn: KFunction<*>,
        objectMapper: ObjectMapper) : AuthorizationDataFetcher(target, fn, objectMapper) {
    override fun get(environment: DataFetchingEnvironment): Any? {
        val result = super.get(environment)
        when (result) {
            is Mono<*> -> return result.toFuture()
            else -> return  result
        }
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


@Component
private object DateCoercing : Coercing<Date, String> {

    override fun parseValue(input: Any?): Date {
        return Date.from(ZonedDateTime.parse(serialize(input)).toInstant())
    }

    override fun parseLiteral(input: Any?): Date? {
        val dateString = (input as? StringValue)?.value
        return Date.from(ZonedDateTime.parse(dateString).toInstant())
    }

    override fun serialize(dataFetcherResult: Any?): String = dataFetcherResult.toString()
}