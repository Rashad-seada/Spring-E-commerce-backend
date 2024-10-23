package com.example.cart_service.dto


data class CartResponse(

    val id: Long? = null,

    val userId: Long,

    val cartItems: List<CartItemResponse> = mutableListOf()

)
