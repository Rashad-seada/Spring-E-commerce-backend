package com.example.spring_project.dto

import java.time.LocalDateTime

data class ProductResponse(
    val id: Long?,
    val name: String?,
    val description: String?,
    val category: Long?,
    val brand: String?,
    val basePrice: Double?,
    val skus: List<SkuResponse>,
    val images: List<String>?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)