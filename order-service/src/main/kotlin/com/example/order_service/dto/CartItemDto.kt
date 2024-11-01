package com.example.order_service.dto

data class CartItemDto(
    val id: Long,
    val productId: Long,
    val skuId: Long,
    val quantity: Int,
    val sku: SkuDto
)