package com.example.api_gateway_server.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun springSecurityFilterChain(serverHttpSecurity: ServerHttpSecurity): SecurityWebFilterChain =
        serverHttpSecurity
            .csrf {
                it.disable()
            }
            .authorizeExchange {
                it
                    .pathMatchers("/eureka/**").permitAll()
                    .pathMatchers("/api/**").permitAll()
                    .anyExchange().authenticated()
            }
            .build()


}
