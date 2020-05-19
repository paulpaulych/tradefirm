package paulpaulych.tradefirm.security.authorization

import com.expediagroup.graphql.annotations.GraphQLDirective

@GraphQLDirective(
        name = "auth",
        description = "This element is great"
)
annotation class Auth(vararg val roles: String)