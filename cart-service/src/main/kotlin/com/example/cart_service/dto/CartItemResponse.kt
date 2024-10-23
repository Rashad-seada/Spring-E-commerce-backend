package com.example.cart_service.dto

import com.example.cart_service.model.Cart

data class CartItemResponse(

    val id: Long? = null,

    var cart: Cart? = null,

    val productId: Long? = null,

    val skuId: Long? = null,

    var quantity: Int? = null
)