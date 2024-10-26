package com.example.cart_service.model

import com.example.cart_service.dto.CartResponse
import jakarta.persistence.*

@Entity
@Table(name = "carts") // Fix the table name
data class Cart(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false) // userId should not be unique if multiple carts are allowed
    val userId: Long? = null,

    @OneToMany(mappedBy = "cart", cascade = [CascadeType.ALL], orphanRemoval = true)
    val cartItems: MutableList<CartItem> = mutableListOf()
)


fun Cart.toCartResponse() : CartResponse {
    return CartResponse(
        id, userId,
        cartItems.map { it.toCartItemResponse() }
    )
}
