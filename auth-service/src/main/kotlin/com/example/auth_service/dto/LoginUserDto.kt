package com.example.auth_service.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class LoginUserDto (
    @field:NotNull(message = "email is required")
    @field:NotBlank(message = "email is required")
    @field:Email(message = "email must be a valid email")
    val email : String?,

    @field:NotNull(message = "password is required")
    @field:NotBlank(message = "password is required")
    @field:Size(min = 2, max = 50, message = "password must be between 2 and 50 characters")
    val password : String?
)