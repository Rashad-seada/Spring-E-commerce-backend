package com.example.spring_project.services

import com.example.spring_project.core.error.CustomException
import com.example.spring_project.core.error.ErrorCode
import com.example.spring_project.model.Product
import com.example.spring_project.repo.ProductRepo
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepo
) {

    @Transactional
    fun saveProduct(product: Product): Product {
        return productRepository.save(product)
    }

    @Transactional
    fun getProductById(id: Long): Product? {
        val product = productRepository.findById(id)
        return if (product.isPresent) product.get() else null
    }

    @Transactional
    fun deleteProduct(id: Long) {
        return productRepository.deleteById(id)
    }

    @Transactional
    fun getAllProducts(page: Int, size: Int): Page<Product> {
        val pageable = PageRequest.of(page, size)
        return productRepository.findAll(pageable)
    }

    @Transactional
    fun searchProductsByName(query: String, page: Int, size: Int): Page<Product> {
        val pageable = PageRequest.of(page, size)
        return productRepository.findByNameIgnoreCaseContaining(query, pageable)
    }

}
