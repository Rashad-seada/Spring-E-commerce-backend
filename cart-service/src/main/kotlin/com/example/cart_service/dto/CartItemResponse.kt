package com.example.cart_service.dto

import com.example.cart_service.model.Cart

data class CartItemResponse(

    val id: Long? = null,

    val productId: Long? = null,

    val skuId: Long? = null,

    var quantity: Int? = null,

    var sku: SkuResponse? = null
)