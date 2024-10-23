package com.example.auth_service.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class TokenDto(

    @field:NotNull(message = "email is required")
    @field:NotBlank(message = "email is required")
    val token: String? = null
)