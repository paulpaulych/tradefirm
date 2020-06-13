package paulpaulych.tradefirm.config.security.login

data class LoginResponse(
        val token: String,
        val expiration: Long,
        val username: String,
        val role: String
)
