package com.example.spring_project.controller

import com.example.spring_project.core.RequestValidator.validateRequest
import com.example.spring_project.core.dto.CustomResponse
import com.example.spring_project.dto.SkuDTO
import com.example.spring_project.dto.SkuResponse
import com.example.spring_project.dto.toSku
import com.example.spring_project.model.Sku
import com.example.spring_project.model.toSkuResponse
import com.example.spring_project.services.ImageService
import com.example.spring_project.services.ProductService
import com.example.spring_project.services.SkuService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/sku")
class SkuController(
    private val skuService: SkuService,
    private val productService: ProductService,
    private  val imageService: ImageService
) {

    @PostMapping
    fun createSku(
        @Valid @RequestBody skuDTO: SkuDTO,
        bindingResult: BindingResult): CustomResponse<SkuResponse> {

        val error = validateRequest<SkuResponse>(bindingResult)
        if(error != null) return error

        val product = skuService.saveSku(skuDTO.toSku(productService))

        return CustomResponse(
            isSuccessful = true,
            message = "The Sku is created successfully",
            data = product.toSkuResponse(imageService)
        )
    }

    @GetMapping("/{id}")
    fun getSkuById(@PathVariable id: Long): CustomResponse<SkuResponse> {
        return CustomResponse(
            isSuccessful = true,
            message = "Got the Sku with id : $id successfully",
            data = skuService.getSkuById(id)?.toSkuResponse(imageService)
        )
    }

    @DeleteMapping("/{id}")
    fun deleteSku(@PathVariable id: Long) : CustomResponse<Unit> {
        return CustomResponse(
            isSuccessful = true,
            message = "Sku with id : $id was deleted successfully",
            data = skuService.deleteSku(id)
        )
    }

    @GetMapping
    fun getAllSkus(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): CustomResponse<Page<SkuResponse>> {

        return CustomResponse(
            isSuccessful = true,
            message = "Got $size sku page number $page successfully",
            data = skuService.getAllSkus(page, size).map { it.toSkuResponse(imageService) }
        )

    }

    @GetMapping("/search")
    fun searchSkusByCode(
        @RequestParam query: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): CustomResponse<Page<SkuResponse>> {
        return CustomResponse(
            isSuccessful = true,
            message = "Searched for \"$query\" and got $size sku page number $page successfully",
            data = skuService.searchSkusByCode(query, page, size).map { it.toSkuResponse(imageService) }
        )
    }

    @GetMapping("/ids")
    fun findSkusByIds(
        @RequestParam ids: List<Long>
    ): CustomResponse<List<SkuResponse>> {
        return CustomResponse(
            isSuccessful = true,
            message = "Searched for $ids ids and got skus successfully",
            data = skuService.findSkusByIds(ids).map { it.toSkuResponse(imageService) }
        )
    }



}
