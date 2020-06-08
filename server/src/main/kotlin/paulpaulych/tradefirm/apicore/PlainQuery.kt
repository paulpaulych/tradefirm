package paulpaulych.tradefirm.apicore

import com.expediagroup.graphql.spring.operations.Query
import kotlin.reflect.KClass

interface PlainQuery<T: Any>: Query {
    fun requestedKClass(): KClass<T>
}
