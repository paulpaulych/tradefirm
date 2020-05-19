package paulpaulych.tradefirm.security

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class WebSecurityConfig(
        private val authenticationManager: AuthenticationManager,
        private val securityContextRepository: SecurityContextRepository
) {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
                .exceptionHandling()
                    .authenticationEntryPoint {
                        swe: ServerWebExchange, _ ->
                            Mono.fromRunnable {
                                swe.response.statusCode = HttpStatus.UNAUTHORIZED
                            }
                    }
                    .accessDeniedHandler {
                        swe: ServerWebExchange, _ ->
                            Mono.fromRunnable {
                                swe.response.statusCode = HttpStatus.FORBIDDEN
                            }
                    }
                .and()
                .csrf()
                    .disable()
                .formLogin()
                    .disable()
                .httpBasic()
                    .disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                    .pathMatchers("/login", "/playground").permitAll()
//                    .pathMatchers("/security").permitAll()
//                    .pathMatchers("/graphql").permitAll()
                    .anyExchange().authenticated()
                .and().build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder{
        return BCryptPasswordEncoder(12)
    }


}