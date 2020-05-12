package paulpaulych.tradefirm.config.graphql

import com.expediagroup.graphql.spring.exception.KotlinDataFetcherExceptionHandler
import com.expediagroup.graphql.spring.exception.SimpleKotlinGraphQLError
import graphql.GraphQL
import graphql.GraphQLError
import graphql.execution.*
import graphql.execution.instrumentation.tracing.TracingInstrumentation
import graphql.schema.GraphQLSchema
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import paulpaulych.utils.LoggerDelegate

@Configuration
class GraphQLConfig {

    @Bean
    fun graphQL(
            schema: GraphQLSchema,
            dataFetcherExceptionHandler: DataFetcherExceptionHandler
    ): GraphQL = GraphQL.newGraphQL(schema)
            .queryExecutionStrategy(AsyncSerialExecutionStrategy(dataFetcherExceptionHandler))
            .mutationExecutionStrategy(AsyncSerialExecutionStrategy(dataFetcherExceptionHandler))
            .subscriptionExecutionStrategy(SubscriptionExecutionStrategy(dataFetcherExceptionHandler))
            .instrumentation(TracingInstrumentation())
            .build()


}

