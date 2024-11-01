package com.example.order_service.dto

data class SkuDto(
    val id: Long,
    val productId: Long,
    val skuCode: String,
    val price: Double,
    val quantity: Int,
    val color: String?,
    val size: String?,
    val images: List<String>
)