package com.example.cart_service.dto


data class CartResponse(

    val id: Long? = null,

    val userId: Long? = null,

    val cartItems: List<CartItemResponse> = mutableListOf()

)
