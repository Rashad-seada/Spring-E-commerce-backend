package com.example.cart_service.dto

data class SkuResponse(
    val id: Long,
    val productId: Long,
    val skuCode: String,
    val price: Double,
    val quantity: Int,
    val color: String?,
    val size: String?,
    val images: List<String>
)
