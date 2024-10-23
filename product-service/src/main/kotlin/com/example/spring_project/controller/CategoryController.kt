package com.example.spring_project.controller

import com.example.spring_project.core.RequestValidator.validateRequest
import com.example.spring_project.core.dto.CustomResponse
import com.example.spring_project.dto.CategoryDTO
import com.example.spring_project.dto.CategoryResponse
import com.example.spring_project.dto.toCategory
import com.example.spring_project.model.Category
import com.example.spring_project.model.toCategoryResponse
import com.example.spring_project.services.CategoryService
import com.example.spring_project.services.ImageService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/category")
class CategoryController(
    private val categoryService: CategoryService,
    private val imageService: ImageService

) {

    @PostMapping
    fun createCategory(
        @Valid @RequestBody categoryDTO: CategoryDTO,
        bindingResult: BindingResult
    ): CustomResponse<CategoryResponse> {

        val error = validateRequest<CategoryResponse>(bindingResult)
        if (error != null) return error

        val parentCategory = categoryDTO.parentCategoryId?.let {
            categoryService.getCategoryById(it)
        }

        val category = categoryService.saveCategory(categoryDTO.toCategory(parentCategory))

        return CustomResponse(
            isSuccessful = true,
            message = "The category is created successfully",
            data = category.toCategoryResponse(imageService)
        )
    }

    @GetMapping("/{id}")
    fun getCategoryById(@PathVariable id: Long): CustomResponse<Category> {
        return CustomResponse(
            isSuccessful = true,
            message = "Got the category with id : $id successfully",
            data = categoryService.getCategoryById(id)
        )
    }

    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Long): CustomResponse<Unit> {
        return CustomResponse(
            isSuccessful = true,
            message = "Category with id : $id was deleted successfully",
            data = categoryService.deleteCategory(id)
        )
    }

    @GetMapping
    fun getAllCategories(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): CustomResponse<Page<CategoryResponse>> {
        return CustomResponse(
            isSuccessful = true,
            message = "Got $size categories page number $page successfully",
            data = categoryService.getAllCategories(page, size).map { it.toCategoryResponse(imageService) }
        )
    }

    @GetMapping("/search")
    fun searchCategories(
        @RequestParam query: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): CustomResponse<Page<CategoryResponse>> {
        return CustomResponse(
            isSuccessful = true,
            message = "Searched for \"$query\" and got $size categories page number $page successfully",
            data = categoryService.searchCategoriesByName(query, page, size).map { it.toCategoryResponse(imageService) }
        )
    }
}
