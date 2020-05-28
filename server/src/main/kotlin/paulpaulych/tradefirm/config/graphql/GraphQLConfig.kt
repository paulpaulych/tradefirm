package paulpaulych.tradefirm.config.graphql

import graphql.GraphQL
import graphql.execution.*
import graphql.execution.instrumentation.tracing.TracingInstrumentation
import graphql.schema.GraphQLSchema
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import simpleorm.core.schema.naming.INamingStrategy
import simpleorm.core.schema.naming.toSnakeCase

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

