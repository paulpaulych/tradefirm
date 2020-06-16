package paulpaulych.tradefirm.config.security.login
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
class NotAuthenticatedException: RuntimeException()
