package com.example.spring_project.model

import com.example.spring_project.dto.CategoryResponse
import com.example.spring_project.services.ImageService
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "categories")
data class Category(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(name = "name", nullable = false, unique = true)
    val name: String = "",

    @Column(name = "description")
    val description: String? = null,

    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference // Indicates that this is the forward part of the relationship
    val products: MutableList<Product> = mutableListOf(),

    @OneToOne(mappedBy = "category", cascade = [CascadeType.ALL], orphanRemoval = true)
    val image: Image? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    @JsonIgnore
    val parentCategory: Category? = null,
)

fun Category.toCategoryResponse(imageService: ImageService) : CategoryResponse {
    return CategoryResponse(
        id,
        name,
        description,
        image?.let { imageService.getImageUrl(it) },
        parentCategory?.id
    )
}

