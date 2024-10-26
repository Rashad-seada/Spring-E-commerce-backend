package com.example.cart_service.dto

data class CustomSkuResponse(
    val isSuccessful: Boolean,
    val message: String,
    val data: List<SkuResponse>,
    val error: String?
)