package com.example.auth_service.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull



data class RegisterUserDto (

    @field:NotNull(message = "username is required")
    @field:NotBlank(message = "username is required")
    @field:Size(min = 2, max = 50, message = "username must be between 2 and 50 characters")
    val username : String? ,

    @field:NotNull(message = "email is required")
    @field:NotBlank(message = "email is required")
    @field:Email(message = "email must be a valid email")
    val email : String?,

    @field:NotNull(message = "password is required")
    @field:NotBlank(message = "password is required")
    @field:Size(min = 2, max = 50, message = "username must be between 2 and 50 characters")
    val password : String?

)