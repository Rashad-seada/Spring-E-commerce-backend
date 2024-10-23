package com.example.spring_project.controller

import com.example.spring_project.core.RequestValidator
import com.example.spring_project.core.RequestValidator.validateRequest
import com.example.spring_project.core.dto.CustomResponse
import com.example.spring_project.dto.ProductDTO
import com.example.spring_project.dto.ProductResponse
import com.example.spring_project.dto.toProduct
import com.example.spring_project.model.Product
import com.example.spring_project.model.toProductDTO
import com.example.spring_project.services.CategoryService
import com.example.spring_project.services.ImageService
import com.example.spring_project.services.ProductService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/product")
class ProductController(
    private val productService: ProductService,
    private val validator: RequestValidator,
    private val categoryService : CategoryService,
    private val imageService: ImageService
) {

    @PostMapping
    fun createProduct(
        @Valid @RequestBody productDTO : ProductDTO,
        bindingResult: BindingResult): CustomResponse<ProductResponse> {

        val error = validateRequest<ProductResponse>(bindingResult)
        if(error != null) return error

        val product = productService.saveProduct(productDTO.toProduct(categoryService))

        return CustomResponse(
            isSuccessful = true,
            message = "The product is created successfully",
            data = product.toProductDTO(imageService)
        )
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): CustomResponse<ProductResponse> {
        return CustomResponse(
            isSuccessful = true,
            message = "Got the product with id : $id successfully",
            data = productService.getProductById(id)?.toProductDTO(imageService)
        )
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long) : CustomResponse<Unit> {
        return CustomResponse(
            isSuccessful = true,
            message = "Product with id : $id was deleted successfully",
            data = productService.deleteProduct(id)
        )
    }

    @GetMapping
    fun getAllProducts(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): CustomResponse<Page<ProductResponse>> {

        return CustomResponse(
            isSuccessful = true,
            message = "Got $size products page number $page successfully",
            data = productService.getAllProducts(page, size).map { it.toProductDTO(imageService) }
        )

    }

    @GetMapping("/search")
    fun searchProducts(
        @RequestParam query: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): CustomResponse<Page<ProductResponse>> {
        return CustomResponse(
            isSuccessful = true,
            message = "Searched for \"$query\" and got $size products page number $page successfully",
            data = productService.searchProductsByName(query, page, size).map { it.toProductDTO(imageService) }
        )
    }



}
