package com.example.spring_project.controller

import com.example.spring_project.core.dto.CustomResponse
import com.example.spring_project.model.Image
import com.example.spring_project.model.ImageOwnerType
import com.example.spring_project.services.CategoryService
import com.example.spring_project.services.ImageService
import com.example.spring_project.services.ProductService
import com.example.spring_project.services.SkuService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/images")
class ImageController(
    private val imageService: ImageService,
    private val productService: ProductService,
    private val categoryService: CategoryService,
    private val skuService: SkuService,

    ) {

    @PostMapping("/upload")
    fun uploadImage(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("ownerId") ownerId: Long,
        @RequestParam("imageOwnerType") imageOwnerType: String
    ): CustomResponse<String> {

        val imageOwnerTypeEnum = ImageOwnerType.valueOf(imageOwnerType.uppercase())

        // Prepare the image object
        val image = Image(
            imageData = file.bytes,
            fileType = file.contentType ?: "application/octet-stream",
            imageOwnerType = imageOwnerTypeEnum,
            ownerId = ownerId
        )

        when (imageOwnerTypeEnum) {
            ImageOwnerType.CATEGORY -> {
                val category = categoryService.getCategoryById(ownerId)
                image.category = category
            }
            ImageOwnerType.PRODUCT -> {
                val product = productService.getProductById(ownerId)
                image.product = product
            }
            ImageOwnerType.SKU -> {
                val sku = skuService.getSkuById(ownerId)
                image.sku = sku
            }
        }


        // Save the image to the database
        val savedImage = imageService.saveImage(image)
        val imageUrl = imageService.getImageUrl(savedImage)

        return CustomResponse(
            isSuccessful = true,
            message = "Image uploaded successfully",
            data = imageUrl
        )
    }


    @GetMapping("/{id}")
    fun getImage(@PathVariable id: Long): ResponseEntity<ByteArray> {
        val image = imageService.getImageById(id)

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(
                image.fileType
                ?: throw RuntimeException("fileType of the image with id : $id is equals to null")
            ))
            .body(image.imageData)
    }

    @DeleteMapping("/{id}")
    fun deleteImage(@PathVariable id: Long): CustomResponse<Unit> {
        imageService.deleteImage(id)

        return CustomResponse(
            isSuccessful = true,
            message = "Image deleted successfully",
            data = null
        )
    }
}
