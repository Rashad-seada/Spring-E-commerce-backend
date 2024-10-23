package com.example.spring_project.services

import com.example.spring_project.core.error.CustomException
import com.example.spring_project.core.error.ErrorCode
import com.example.spring_project.model.Category
import com.example.spring_project.repo.CategoryRepo
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryService(
    private val categoryRepository: CategoryRepo
) {

    @Transactional
    fun saveCategory(category: Category): Category {
        return categoryRepository.save(category)
    }

    @Transactional
    fun getCategoryById(id: Long): Category? {

        val category = categoryRepository.findById(id)

        return if (category.isPresent) category.get() else null

    }

    @Transactional
    fun deleteCategory(id: Long) {
        categoryRepository.deleteById(id)
    }

    @Transactional
    fun getAllCategories(page: Int, size: Int): Page<Category> {
        val pageable = PageRequest.of(page, size)
        return categoryRepository.findAll(pageable)
    }

    @Transactional
    fun searchCategoriesByName(query: String, page: Int, size: Int): Page<Category> {
        val pageable = PageRequest.of(page, size)
        return categoryRepository.findByNameContainingIgnoreCase(query, pageable)
    }
}
