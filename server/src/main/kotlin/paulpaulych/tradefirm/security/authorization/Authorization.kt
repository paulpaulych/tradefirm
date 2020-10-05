package paulpaulych.tradefirm.security.authorization

import com.expediagroup.graphql.annotations.GraphQLDirective

@GraphQLDirective(
        name = "auth"
)
annotation class Authorization(vararg val roles: String)

