package com.example.cart_service.model

import com.example.cart_service.dto.CartItemResponse
import jakarta.persistence.*

@Entity
@Table(name = "cart_items") // Use correct table name for cart items
data class CartItem(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY) // Fix lazy loading to avoid performance issues
    @JoinColumn(name = "cart_id", nullable = false) // Foreign key for the cart
    var cart: Cart? = null,

    @Column(name = "product_id", nullable = false) // Reference to a product
    val productId: Long?,

    @Column(name = "sku_id", nullable = false) // Reference to the specific variant of the product
    val skuId: Long?,

    @Column(name = "quantity", nullable = false)
    var quantity: Int = 1
)

fun CartItem.toCartItemResponse() : CartItemResponse {
    return CartItemResponse(
      id, cart, productId, skuId, quantity
    )
}
