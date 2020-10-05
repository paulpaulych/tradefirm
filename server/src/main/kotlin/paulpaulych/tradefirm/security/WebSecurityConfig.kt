package paulpaulych.tradefirm.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.cors.reactive.CorsConfigurationSource
import paulpaulych.tradefirm.security.authentication.AuthenticationManager
import paulpaulych.tradefirm.security.authentication.TokenWebFilter
import reactor.core.publisher.Mono


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class WebSecurityConfig(
        private val authenticationManager: AuthenticationManager,
        private val tokenWebFilter: TokenWebFilter
) {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
                .exceptionHandling()
                    .authenticationEntryPoint { swe, _ ->
                        Mono.fromRunnable {
                            swe.response.statusCode = HttpStatus.UNAUTHORIZED
                        }
                    }
                    .accessDeniedHandler { swe, _ ->
                        Mono.fromRunnable {
                            swe.response.statusCode = HttpStatus.FORBIDDEN
                        }
                    }
                .and()
                .cors()
                    .configurationSource(corsConfigurationSource())
                .and()
                .csrf()
                    .disable()
                .formLogin()
                    .disable()
                .httpBasic()
                    .disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(tokenWebFilter)
                .authorizeExchange()
                    .pathMatchers("/login", "/playground").permitAll()
                    .anyExchange().authenticated()
                .and().build()
    }

    fun corsConfigurationSource(): CorsConfigurationSource{
        val config = CorsConfiguration().apply {
            addAllowedOrigin("*")
            addAllowedHeader("*");
            addAllowedMethod("GET");
            addAllowedMethod("POST");
        }
        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/*", config)
        }
    }
}

@Configuration
class PasswordEncoderConfig{

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(12)
    }
}