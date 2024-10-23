package com.example.spring_project.model

import com.example.spring_project.dto.ProductResponse
import com.example.spring_project.services.ImageService
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "products")
data class Product(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(name = "name", nullable = false)
    val name: String? = null,

    @Column(name = "description", nullable = false)
    val description: String? = null,

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference // Prevents infinite recursion by ignoring this field during serialization
    val category: Category? = null,

    @Column(name = "brand", nullable = true)
    val brand: String? = null,

    @Column(name = "base_price", nullable = false)
    val basePrice: Double? = null,  // Common base price for all SKUs

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], orphanRemoval = true)
    val skus: List<Sku> = mutableListOf(),

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonBackReference // Prevents infinite recursion by ignoring this field during serialization
    val images: MutableList<Image> = mutableListOf(),

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()

)

// Convert Product entity to ProductDTO
fun Product.toProductDTO(imageService: ImageService): ProductResponse {
    return ProductResponse(
        id = id,
        name = name,
        description = description,
        category = category?.id,
        brand = brand,
        basePrice = basePrice,
        skus = skus.map { it.toSkuResponse(imageService) },
        images = images.map { imageService.getImageUrl(it) },
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

