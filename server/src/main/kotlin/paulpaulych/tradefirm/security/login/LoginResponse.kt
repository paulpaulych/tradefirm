package paulpaulych.tradefirm.security.login

data class LoginResponse(
        val token: String,
        val expiration: Long
)