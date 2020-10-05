package paulpaulych.tradefirm.security.authorization

import com.expediagroup.graphql.execution.FunctionDataFetcher
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import graphql.schema.DataFetchingEnvironment
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation

/**
 * Handler for [Authorization]
 */
open class AuthorizationDataFetcher(
        target: Any?,
        private val fn: KFunction<*>,
        objectMapper: ObjectMapper = jacksonObjectMapper()) : FunctionDataFetcher(target, fn, objectMapper) {

    override fun get(environment: DataFetchingEnvironment): Any? {
        val graphQLContext = environment.getContext<SecurityGraphQLContext>()

        val requiredRoles = fn.findAnnotation<Authorization>() ?.roles
                ?: return super.get(environment)

        if (graphQLContext.securityContext == null){
            error("security context is null")
        }
        val grantedAuthorities = graphQLContext.securityContext.authentication.authorities.map { it.authority }
        val absentRoles = requiredRoles.filter {
            !grantedAuthorities.contains(it)
        }
        if (absentRoles.isNotEmpty()) {
            throw error("Can only access this resource with roles: ${requiredRoles.joinToString(", ")}")
        }
        return super.get(environment)
    }
}