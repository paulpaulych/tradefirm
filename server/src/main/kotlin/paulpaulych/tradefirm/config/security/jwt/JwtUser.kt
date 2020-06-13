package paulpaulych.tradefirm.config.security.jwt

data class JwtUser (
    var userName: String,
    var password: String,
    var role: String
)