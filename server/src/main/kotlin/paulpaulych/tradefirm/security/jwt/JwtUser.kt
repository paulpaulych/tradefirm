package paulpaulych.tradefirm.security.jwt

data class JwtUser (
    var userName: String,
    var password: String,
    var role: String
)