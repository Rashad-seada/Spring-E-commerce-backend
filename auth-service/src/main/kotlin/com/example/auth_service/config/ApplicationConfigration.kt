package com.example.auth_service.config

import com.example.auth_service.repo.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@Configuration
class ApplicationConfigratio @Autowired constructor(
    private val userRepo: UserRepo
) {

    @Bean
    fun webClientBuilder(): WebClient.Builder {
        return WebClient.builder()
    }



    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username ->
            userRepo.findByEmail(username)
                ?: throw UsernameNotFoundException("User not found")
        }
    }

    @Bean
    fun passwordEncoder() : BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authentecationManger(config: AuthenticationConfiguration) : AuthenticationManager {
        return config.authenticationManager
    }

    @Bean
    fun authentecationProvider(): AuthenticationProvider {
        val authProvider : DaoAuthenticationProvider = DaoAuthenticationProvider()

        authProvider.setUserDetailsService(userDetailsService())
        authProvider.setPasswordEncoder(passwordEncoder())

        return authProvider
    }




}