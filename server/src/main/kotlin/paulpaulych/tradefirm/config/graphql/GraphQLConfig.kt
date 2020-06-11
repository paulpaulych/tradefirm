package paulpaulych.tradefirm.config.graphql

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import graphql.GraphQL
import graphql.execution.*
import graphql.execution.instrumentation.tracing.TracingInstrumentation
import graphql.schema.GraphQLSchema
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

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
            .build()

}

