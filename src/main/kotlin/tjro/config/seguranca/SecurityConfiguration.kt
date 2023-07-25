package tjro.config.seguranca

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import tjro.service.TokenService


@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val userDetailsService: UserDetailsService,
    private val tokenService: TokenService,
    private val securityFilter: SecurityFilter
) {

    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain {

        http.invoke {
            csrf { disable() }
            authorizeRequests {
                authorize("/h2-console/**", permitAll)
                authorize("/login", permitAll)
                authorize("/usuarios/cadastro", permitAll)
                authorize("/usuarios/codigo-senha", permitAll)
                authorize("/usuarios/alterar-senha", permitAll)
                authorize(anyRequest, authenticated)
            }
            http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter::class.java)
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            headers { frameOptions { disable() } }
            httpBasic { }
        }
        return http.build()
    }

    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager? {
        return authenticationConfiguration.authenticationManager
    }

    fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userDetailsService)?.passwordEncoder(BCryptPasswordEncoder())
    }

}