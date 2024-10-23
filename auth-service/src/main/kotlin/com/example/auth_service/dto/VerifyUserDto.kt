package com.example.auth_service.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class VerifyUserDto (

    @field:NotNull(message = "email is required")
    @field:NotBlank(message = "email is required")
    @field:Email(message = "email must be a valid email")
    val email : String?,

    @field:NotNull(message = "verificationCode is required")
    @field:NotBlank(message = "verificationCode is required")
    val verificationCode : String?

)