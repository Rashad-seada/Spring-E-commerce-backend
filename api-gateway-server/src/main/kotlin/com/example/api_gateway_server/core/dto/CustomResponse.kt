package com.example.api_gateway_server.core.dto

data class CustomResponse<T>(
    val isSuccessful : Boolean,
    val message: String,
    val data : T? = null,
    val error: CustomError? = null
)

data class CustomError(
    val errorCode : Int,
    val message: String
)
