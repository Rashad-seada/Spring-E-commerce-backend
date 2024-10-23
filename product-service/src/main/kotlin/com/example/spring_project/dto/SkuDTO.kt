package com.example.spring_project.dto

import com.example.spring_project.core.error.CustomException
import com.example.spring_project.core.error.ErrorCode
import com.example.spring_project.model.Sku
import com.example.spring_project.services.ProductService
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size



import java.math.BigDecimal

data class SkuDTO(

    @field:Positive
    val id: Long? = null,

    @field:NotNull(message = "skuCode is required")
    @field:Size(min = 2, max = 100, message = "skuCode must be between 2 and 100 characters")
    val skuCode: String?,

    @field:NotNull(message = "price is required")
    @field:Positive(message = "price must be greater than 0")
    val price: BigDecimal?,

    @field:NotNull(message = "quantity is required")
    @field:Positive(message = "quantity must be greater than 0")
    val quantity: Int?,

    @field:Size(min = 2, max = 100, message = "color must be between 2 and 100 characters")
    val color: String? = null,

    @field:Size(min = 1, max = 10, message = "size must be between 1 and 10 characters")
    val size: String? = null,

    @field:NotNull(message = "productId is required")
    val productId: Long?
)

fun SkuDTO.toSku(productService: ProductService): Sku {

    this.productId ?: throw CustomException.create(ErrorCode.VALIDATION_ERROR)

    val product = productService.getProductById(this.productId)
        ?: throw IllegalArgumentException("Product not found with id: ${this.productId}")

    return Sku(
        id = this.id,
        skuCode = this.skuCode ?: throw CustomException.create(ErrorCode.VALIDATION_ERROR),
        price = this.price ?: throw CustomException.create(ErrorCode.VALIDATION_ERROR),
        quantity = this.quantity ?: throw CustomException.create(ErrorCode.VALIDATION_ERROR),
        color = this.color,
        size = this.size,
        product = product
    )
}


