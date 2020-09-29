package paulpaulych.tradefirm.dbcommon

import com.expediagroup.graphql.spring.operations.Mutation
import com.expediagroup.graphql.spring.operations.Query
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.apicore.FilterDTO
import paulpaulych.tradefirm.apicore.PageRequestDTO
import paulpaulych.tradefirm.apicore.PageRequestMapper
import paulpaulych.tradefirm.apicore.toFetchFilter
import paulpaulych.tradefirm.config.graphql.badInputError
import paulpaulych.tradefirm.config.security.common.Authorization
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.SimpleOrm
import simpleorm.core.pagination.Page
import kotlin.reflect.KClass

@Component
class DbCommonQuery(
        private val pageRequestMapper: PageRequestMapper,
        private val plainClassesRegistry: PlainClassesRegistry
): Query {

    @Authorization("ROLE_ADMIN")
    fun plainEntitiesPage(type: String, filterDTO: FilterDTO?, pageRequestDTO: PageRequestDTO): Page<Any>{
        val kClass = plainClassesRegistry.getByTypeName(type)
        return SimpleOrm.findBy(
                kClass,
                filter = toFetchFilter(kClass, filterDTO),
                pageable = pageRequestMapper.getPageRequest(kClass, pageRequestDTO)
        )
    }
}

@Component
class DbCommonMutation(
        private val plainClassesRegistry: PlainClassesRegistry,
        private val objectMapper: ObjectMapper
): Mutation {

    private val log by LoggerDelegate()

    @Authorization("ROLE_ADMIN")
    suspend fun batchInsertPlainEntities(type: String, values: List<Any>): List<Any> {
        log.error("hello")
        val kClass = plainClassesRegistry.getByTypeName(type)
        return SimpleOrm.batchInsert(kClass, values.map { convert(it, kClass) })
    }

    @Authorization("ROLE_ADMIN")
    suspend fun updatePlainEntity(type: String, value: Any): Any {
        val kClass = plainClassesRegistry.getByTypeName(type)
        val plainObject = convert(value, kClass)
        log.error("value to save: $plainObject")
        return SimpleOrm.persist(kClass, plainObject)
    }

    @Authorization("ROLE_ADMIN")
    suspend fun deletePlainEntity(type: String, ids: List<Long>): List<Long>{
        val kClass = plainClassesRegistry.getByTypeName(type)
        ids.forEach {
            SimpleOrm.delete(kClass, it)
        }
        return ids
    }

    private suspend fun <T: Any> convert(input: T, kClass: KClass<T>): T{
        try {
            return objectMapper.convertValue(input, kClass.java)
        }catch (e: IllegalArgumentException){
            throw badInputError("error on reading input variables: ${e.message}")
        }
    }
}