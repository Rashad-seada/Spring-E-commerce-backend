package com.example.auth_service.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class SecurityConfigration @Autowired constructor(
    val authentecationProvider: AuthenticationProvider,
    val jwtAuthentecationFilter: JwtAuthentecationFilter
) {

    @Bean
    fun SecuretyFilterChain(http : HttpSecurity) : SecurityFilterChain {
        http.csrf {
            it.disable()
        }.authorizeHttpRequests {
            it.requestMatchers("/api/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated()
        }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authentecationProvider)
            .addFilterBefore(jwtAuthentecationFilter,UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
     fun corsConfigrationSource() : CorsConfigurationSource {
         val configuration : CorsConfiguration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:8080")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("Authorization","Content-Type")

        val source : UrlBasedCorsConfigurationSource = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**",configuration)
        return source
     }


}