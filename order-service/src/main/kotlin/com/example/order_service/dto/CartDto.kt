package com.example.order_service.dto

data class CartDto(
    val id: Int,
    val userId: Int,
    val cartItems: List<CartItemDto>
)