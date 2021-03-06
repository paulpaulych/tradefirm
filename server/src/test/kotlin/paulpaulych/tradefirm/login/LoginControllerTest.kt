package paulpaulych.tradefirm.login

import io.kotlintest.specs.FunSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import paulpaulych.tradefirm.security.JwtService
import paulpaulych.tradefirm.security.Role
import paulpaulych.tradefirm.security.User
import paulpaulych.tradefirm.security.UserRepository
import paulpaulych.utils.LoggerDelegate
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class LoginControllerTest: FunSpec(){

    private val userRepositoryMock: UserRepository = mockk()

    private val passwordEncoder = BCryptPasswordEncoder()

    private val log by LoggerDelegate()

    init {

        val userStub = User(
                "admin",
                passwordEncoder.encode("admin"),
                true,
                listOf(Role.ROLE_ADMIN))

        every { userRepositoryMock.findByUsername(eq("admin")) } returns Mono.just(userStub)

        every { userRepositoryMock.findByUsername(neq("admin")) } returns Mono.empty()

        val controller = LoginController(
                passwordEncoder,
                JwtService("ssssssssssssssssssssssssssssssssssssssssssssssssssssss"),
                userRepositoryMock)

        test("user service test"){
            val origUserService = UserRepository(passwordEncoder)
            origUserService.init()
            StepVerifier.create(origUserService.findByUsername("admin"))
                    .expectNextMatches{
                        log.info("${it.username}")
                        it.username == userStub.username
                    }
                    .verifyComplete()
        }

        test("successful"){
            val res = controller.login(LoginRequest("admin", "admin"))
            StepVerifier.create(res)
                    .expectNextMatches{
                        it.role == "ROLE_ADMIN"
                                && it.username == "admin"
                    }
                    .expectComplete()
                    .verify()

        }

        test("incorrect username"){
            val res = controller.login(LoginRequest("", "admin"))
            StepVerifier.create(res)
                    .expectError()
                    .verify()
        }
    }


}