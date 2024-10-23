package com.example.auth_service.dto
import com.example.auth_service.model.User

data class LoginResponse(
    val token : String,
    val user : User,
)