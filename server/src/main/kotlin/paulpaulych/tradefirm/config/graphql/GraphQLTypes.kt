package paulpaulych.tradefirm.config.graphql

import graphql.Scalars
import graphql.scalars.ExtendedScalars
import graphql.schema.*

internal val orderType = GraphQLEnumType.newEnum()
        .name("Order")
        .value("ASC")
        .value("DESC")
        .build()

internal val dateScalarType = GraphQLScalarType.newScalar()
        .name("Date")
        .coercing(DateCoercing)
        .build()

internal val sortType = GraphQLInputObjectType.newInputObject()
        .name("SortInput")
        .field(GraphQLInputObjectField.newInputObjectField()
                .name("field")
                .type(Scalars.GraphQLString)
                .build())
        .field(GraphQLInputObjectField.newInputObjectField()
                .name("order")
                .type(orderType)
                .build())
        .build()

internal val pageRequestType = GraphQLInputObjectType.newInputObject()
        .name("PageRequestInput")
        .field(GraphQLInputObjectField.newInputObjectField()
                .name("pageNumber")
                .type(Scalars.GraphQLInt)
                .build())
        .field(GraphQLInputObjectField.newInputObjectField()
                .name("pageSize")
                .type(Scalars.GraphQLInt)
                .build())
        .field(GraphQLInputObjectField.newInputObjectField()
                .name("sorts")
                .type(GraphQLList.list(sortType))
                .build())
        .build()

internal val pageInfoType = GraphQLObjectType.newObject()
        .name("PageInfo")
        .field(GraphQLFieldDefinition.newFieldDefinition()
                .name("pageSize")
                .type(Scalars.GraphQLInt)
                .build())
        .build()

internal val pageType = GraphQLObjectType.newObject()
        .name("Page")
        .field(GraphQLFieldDefinition.newFieldDefinition()
                .name("pageInfo")
                .type(pageInfoType)
                .build())
        .field(GraphQLFieldDefinition.newFieldDefinition()
                .name("values")
                .type(ExtendedScalars.Json))
        .build()
