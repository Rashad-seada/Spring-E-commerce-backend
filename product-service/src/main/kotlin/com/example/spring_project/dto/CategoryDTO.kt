package com.example.spring_project.dto

import com.example.spring_project.core.error.CustomException
import com.example.spring_project.core.error.ErrorCode
import com.example.spring_project.model.Category
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

data class CategoryDTO(

    @field:Positive
    val id: Long? = null,

    @field:NotNull(message = "name is required")
    @field:Size(min = 2, max = 100, message = "name must be between 2 and 100 characters")
    val name: String?,

    @field:Size(min = 2, max = 255, message = "description must be between 2 and 255 characters")
    val description: String? = null,

    @field:Positive(message = "parentCategoryId must be a positive number")
    val parentCategoryId: Long? = null
)

fun CategoryDTO.toCategory(parentCategory: Category?): Category {
    return Category(
        id = id,
        name = this.name ?: throw CustomException.create(ErrorCode.VALIDATION_ERROR),
        description = this.description,
        parentCategory = parentCategory
    )
}
