package com.example.order_service.models

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "order_items")
data class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(nullable = false)
    val productId: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY) // Fix lazy loading to avoid performance issues
    @JoinColumn(name = "order_id", nullable = false) // Foreign key for the cart
    @JsonBackReference // Prevents infinite recursion
    var order : Order? =  null,

    @Column(nullable = false)
    val skuId: Long? = null,

    @Column(nullable = false)
    val quantity: Int? = null,

    @Column(nullable = false)
    val price: Double? = null,

)