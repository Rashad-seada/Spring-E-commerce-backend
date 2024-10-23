package com.example.spring_project.dto

import com.example.spring_project.model.Category
import com.example.spring_project.model.Image

data class CategoryResponse(

    val id: Long? = null,

    val name: String = "",

    val description: String? = null,

    val image: String? = null,

    val parentCategory: Long? = null,
)
