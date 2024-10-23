package com.example.spring_project.model

import com.example.spring_project.dto.SkuResponse
import com.example.spring_project.services.ImageService
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "skus")
data class Sku(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(name = "sku_code", nullable = false, unique = true)
    val skuCode: String? = null,

    @Column(name = "price", nullable = false)
    val price: BigDecimal? = null,

    @Column(name = "quantity", nullable = false)
    val quantity: Int? = null,

    @Column(name = "color", nullable = true)
    val color: String? = null,

    @Column(name = "size", nullable = true)
    val size: String? = null,

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference
    val product: Product? = null,

    @OneToMany(mappedBy = "sku", cascade = [CascadeType.ALL], orphanRemoval = true)
    val images: MutableList<Image> = mutableListOf()
)

fun Sku.toSkuResponse(imageService: ImageService) : SkuResponse {
    return SkuResponse(
        id,product?.id, skuCode, price, quantity, color, size, images.map { imageService.getImageUrl(it) }
    )
}


