package com.example.order_service.dto

import jakarta.validation.constraints.NotNull

data class OrderRequest(

    @field:NotNull(message = "streetAddress is required")
    val streetAddress: String?= null,

    @field:NotNull(message = "city is required")
    val city: String?= null,

    @field:NotNull(message = "state is required")
    val state: String?= null,

    @field:NotNull(message = "postalCode is required")
    val postalCode: String?= null,

    @field:NotNull(message = "country is required")
    val country: String?= null,

    // Contact fields
    @field:NotNull(message = "contactName is required")
    val contactName: String?= null,

    @field:NotNull(message = "contactNumber is required")
    val contactNumber: String?= null
)
