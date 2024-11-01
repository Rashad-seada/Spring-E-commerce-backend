package com.example.order_service.models

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?= null,

    @Column(nullable = false)
    val userId: Long?= null,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val items: List<OrderItem> = mutableListOf(),

    @Column(nullable = false)
    val totalAmount: Double?= null,

    @Column(nullable = false)
    val orderDate: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.PENDING,

    @Column(nullable = false)
    val streetAddress: String?= null,

    @Column(nullable = false)
    val city: String?= null,

    @Column(nullable = false)
    val state: String?= null,

    @Column(nullable = false)
    val postalCode: String?= null,

    @Column(nullable = false)
    val country: String?= null,

    // Contact fields
    @Column(nullable = false)
    val contactName: String?= null,

    @Column(nullable = false)
    val contactNumber: String?= null
)


enum class OrderStatus {
    PENDING, COMPLETED, CANCELED
}
