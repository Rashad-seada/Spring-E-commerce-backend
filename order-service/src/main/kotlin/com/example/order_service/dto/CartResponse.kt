package com.example.order_service.dto

import com.example.order_service.core.dto.CustomError

data class CartResponse(
    val isSuccessful: Boolean,
    val message: String,
    val data: CartDto?,
    val error: CustomError?
)