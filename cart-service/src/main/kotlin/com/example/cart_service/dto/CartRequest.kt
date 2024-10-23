package com.example.cart_service.dto

import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotNull

data class CartRequest(

    @field:NotNull(message = "productId is required")
    @field:Digits(integer = 10, fraction = 0, message = "productId must be a valid integer")
    val productId: Long?,

    @field:NotNull(message = "skuId is required")
    @field:Digits(integer = 10, fraction = 0, message = "skuId must be a valid integer")
    val skuId: Long?,

    @field:NotNull(message = "quantity is required")
    @field:Digits(integer = 10, fraction = 0, message = "quantity must be a valid integer")
    var quantity: Int = 1,

)
