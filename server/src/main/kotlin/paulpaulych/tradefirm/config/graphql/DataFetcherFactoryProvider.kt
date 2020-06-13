package paulpaulych.tradefirm.config.graphql

import com.expediagroup.graphql.execution.SimpleKotlinDataFetcherFactoryProvider
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.schema.DataFetcherFactory
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.apicore.PageRequestMapper
import kotlin.reflect.KFunction

@Component
class DataFetcherFactoryProvider(
        private val objectMapper: ObjectMapper
) : SimpleKotlinDataFetcherFactoryProvider(objectMapper) {

    override fun functionDataFetcherFactory(target: Any?, kFunction: KFunction<*>): DataFetcherFactory<Any?>
            = DataFetcherFactory<Any?> {
                MonoSupportedFunctionDataFetcher(
                        target = target,
                        fn = kFunction,
                        objectMapper = objectMapper
                )
            }
}