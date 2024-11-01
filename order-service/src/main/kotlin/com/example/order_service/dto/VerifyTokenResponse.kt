package com.example.order_service.dto

import com.example.order_service.core.dto.CustomError


data class VerifyTokenResponse(
    val isSuccessful: Boolean,
    val message: String,
    val data: VerifyTokenData?,
    val error: CustomError
    ?
)

data class VerifyTokenData(
    val verified: Boolean,
    val tokenData: TokenData?
)

data class TokenData(
    val userId: Int,
    val roles: List<String>,
    val sub: String,
    val iat: Long,
    val exp: Long
)
