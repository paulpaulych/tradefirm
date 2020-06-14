package paulpaulych.tradefirm.config.graphql

import com.expediagroup.graphql.hooks.SchemaGeneratorHooks
import graphql.scalars.ExtendedScalars
import graphql.schema.GraphQLType
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.apicore.PageInfo
import paulpaulych.utils.LoggerDelegate
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import simpleorm.core.pagination.Page
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KType


@Component
class Hooks : SchemaGeneratorHooks {

    private val log by LoggerDelegate()

    override fun willResolveMonad(type: KType): KType = when (type.classifier) {
        Mono::class -> type.arguments.firstOrNull()?.type
        Flux::class -> type.arguments.firstOrNull() ?.type
        else -> type
    } ?: type

    override fun willGenerateGraphQLType(type: KType): GraphQLType? = when (type.classifier as? KClass<*>) {
        Date::class -> dateScalarType
        Any::class -> ExtendedScalars.Json
        PageInfo::class -> pageInfoType
        Page::class -> pageType
        else -> null
    }

}
