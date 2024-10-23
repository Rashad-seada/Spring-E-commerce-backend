package com.example.spring_project.dto

import com.example.spring_project.core.error.CustomException
import com.example.spring_project.core.error.ErrorCode
import com.example.spring_project.model.Product
import com.example.spring_project.services.CategoryService
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size


data class ProductDTO(

    @field:Positive
    val id: Long? = null,

    @field:NotNull(message = "name is required")
    @field:Size(min = 2, max = 100, message = "name must be between 2 and 100 characters")
    val name: String?,

    @field:NotNull(message = "description is required")
    @field:Size(min = 5, message = "description must be at least 5 characters long")
    val description: String?,

    @field:Size(min = 2, max = 100, message = "brand must be between 2 and 100 characters")
    val brand: String? = null,

    @field:NotNull(message = "basePrice is required")
    @field:Positive( message = "basePrice must be 0$ minimum")
    val basePrice: Double? = 0.0,

    @field:NotNull(message = "categoryId is required")
    val categoryId: Long?,

)

fun ProductDTO.toProduct(categoryService: CategoryService) : Product {

    this.categoryId ?: throw CustomException.create(ErrorCode.VALIDATION_ERROR)

    val category = categoryService.getCategoryById(this.categoryId)
        ?: throw IllegalArgumentException("Category not found with id: ${this.categoryId}")


    return Product(
        id = id,
        name = this.name ?: throw CustomException.create(ErrorCode.VALIDATION_ERROR),
        description = this.description ?: throw CustomException.create(ErrorCode.VALIDATION_ERROR),
        brand = this.brand,
        basePrice = this.basePrice ?: throw CustomException.create(ErrorCode.VALIDATION_ERROR),
        category = category
    )

}