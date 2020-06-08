package paulpaulych.tradefirm.config.graphql

import com.expediagroup.graphql.execution.SimpleKotlinDataFetcherFactoryProvider
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.schema.DataFetcherFactory
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.apicore.PageRequestMapper
import paulpaulych.utils.LoggerDelegate
import kotlin.reflect.KFunction

@Component
class CustomDataFetcherFactoryProvider(
        private val objectMapper: ObjectMapper,
        private val pageRequestMapper: PageRequestMapper
) : SimpleKotlinDataFetcherFactoryProvider(objectMapper) {

    private val log by LoggerDelegate()

    override fun functionDataFetcherFactory(target: Any?, kFunction: KFunction<*>): DataFetcherFactory<Any?>
            = DataFetcherFactory<Any?> {
                CustomFunctionDataFetcher(
                        target = target,
                        fn = kFunction,
                        objectMapper = objectMapper,
                        pageRequestMapper = pageRequestMapper
                )
            }
}