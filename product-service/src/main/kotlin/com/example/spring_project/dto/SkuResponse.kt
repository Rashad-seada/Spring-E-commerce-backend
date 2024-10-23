package com.example.spring_project.dto

import java.math.BigDecimal


data class SkuResponse(

    val id: Long? = null,

    val productId: Long? = null,

    val skuCode: String?,

    val price: BigDecimal?,

    val quantity: Int?,

    val color: String? = null,

    val size: String? = null,

    val images: List<String> = mutableListOf(),
)

