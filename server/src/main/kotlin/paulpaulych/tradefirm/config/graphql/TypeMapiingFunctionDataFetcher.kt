package paulpaulych.tradefirm.config.graphql

import com.fasterxml.jackson.databind.ObjectMapper
import graphql.schema.DataFetchingEnvironment
import paulpaulych.tradefirm.apicore.PageRequestDTO
import paulpaulych.tradefirm.apicore.PageRequestMapper
import paulpaulych.tradefirm.apicore.PlainQuery
import paulpaulych.tradefirm.security.AuthorizationDataFetcher
import paulpaulych.utils.LoggerDelegate
import reactor.core.publisher.Mono
import simpleorm.core.pagination.PageRequest
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter

class CustomFunctionDataFetcher(
        private val target: Any?,
        private val fn: KFunction<*>,
        private val objectMapper: ObjectMapper,
        private val pageRequestMapper: PageRequestMapper
): AuthorizationDataFetcher(target, fn, objectMapper) {

    private val log by LoggerDelegate()

    override fun get(environment: DataFetchingEnvironment): Any? {
        return convertResult(super.get(environment))
    }

    private fun convertResult(result: Any?): Any? =
            when (result) {
                is Mono<*> -> result.toFuture()
                else -> result
            }

    override fun convertParameterValue(param: KParameter, environment: DataFetchingEnvironment): Any? {
        when(param.type.classifier as KClass<*>){
            PageRequest::class -> {
                val name = param.name
                val argument = environment.arguments[name]
                val javaClass = PageRequestDTO::class.java
                val pageRequestDTO = objectMapper.convertValue(argument, javaClass)
                val queryObj = target as? PlainQuery<*>
                        ?: error("cannot cast target to PlainQuery")
                val pageReq = pageRequestMapper.getPageRequest(queryObj.requestedKClass(), pageRequestDTO)
                log.info("pageReq: $pageReq")
                return pageReq
            }
            else -> return super.convertParameterValue(param, environment)
        }
    }

}
