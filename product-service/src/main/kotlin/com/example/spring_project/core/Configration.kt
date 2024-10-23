package com.example.spring_project.core

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Configration {

    @Bean
    fun getRequestValidator() : RequestValidator {
        return RequestValidator
    }

}