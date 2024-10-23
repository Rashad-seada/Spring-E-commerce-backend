package com.example.spring_project.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "images")
data class Image(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(name = "image_data", nullable = false)
    @JsonIgnore  // Prevents serialization of the image data
    val imageData: ByteArray? = null,

    @Column(name = "file_type", nullable = false)
    val fileType: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "image_owner_type", nullable = false)
    val imageOwnerType: ImageOwnerType? = null,

    @Column(name = "owner_id", nullable = false)
    val ownerId: Long? = null,

    @OneToOne
    @JoinColumn(name = "category_id")
    @JsonManagedReference
    var category: Category? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonManagedReference
    var product: Product? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id")
    @JsonManagedReference
    var sku : Sku? = null


) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Image

        if (id != other.id) return false
        if (!imageData.contentEquals(other.imageData)) return false
        if (fileType != other.fileType) return false
        if (imageOwnerType != other.imageOwnerType) return false
        if (ownerId != other.ownerId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + imageData.contentHashCode()
        result = 31 * result + fileType.hashCode()
        result = 31 * result + imageOwnerType.hashCode()
        result = 31 * result + ownerId.hashCode()
        return result
    }
}

enum class ImageOwnerType {
    CATEGORY,
    PRODUCT,
    SKU

}

