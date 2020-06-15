package paulpaulych.tradefirm.config.security.common

import com.expediagroup.graphql.execution.FunctionDataFetcher
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import graphql.schema.DataFetchingEnvironment
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation

open class AuthorizationDataFetcher(
        private val target: Any?,
        private val fn: KFunction<*>,
        private val objectMapper: ObjectMapper = jacksonObjectMapper()) : FunctionDataFetcher(target, fn, objectMapper) {

    override fun get(environment: DataFetchingEnvironment): Any? {
        val graphQLContext = environment.getContext<MyGraphQLContext>()

        val requiredRoles = fn.findAnnotation<Authorization>()
                ?. roles
        if(requiredRoles == null){
            return super.get(environment)
        }
        if (graphQLContext.securityContext == null){
            error("security context is null")
        }
        val grantedAuthorities = graphQLContext.securityContext.authentication.authorities.map { it.authority }
        val absentRoles = requiredRoles.filter {
            !grantedAuthorities.contains(it)
        }
        if (!absentRoles.isEmpty()) {
            throw error("Can only access this resource with roles: ${requiredRoles.joinToString(", ")}")
        }
        return super.get(environment)
    }
}