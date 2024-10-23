package com.example.cart_service.service

import com.example.cart_service.model.Cart
import com.example.cart_service.model.CartItem
import com.example.cart_service.repo.CartRepo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CartService(private val cartRepo: CartRepo) {

    @Transactional
    fun getCartByUserId(userId: Long): Cart {
        return cartRepo.findByUserId(userId) ?: createCartForUser(userId)
    }

    private fun createCartForUser(userId: Long): Cart {
        val cart = Cart(userId = userId)
        return cartRepo.save(cart)
    }

    @Transactional
    fun addCartItem(userId: Long, productId: Long?,skuId : Long?, quantity: Int = 1): Cart {
        val cart = getCartByUserId(userId)
        val existingItem = cart.cartItems.find { it.productId == productId }

        if (existingItem != null) {
            existingItem.quantity += quantity
        } else {
            val newItem = CartItem(
                cart = cart,
                productId = productId,
                quantity = quantity,
                skuId = skuId,
            )
            cart.cartItems.add(newItem)
        }
        return cartRepo.save(cart)
    }

    @Transactional
    fun removeCartItem(userId: Long, cartItemId: Long): Cart {
        val cart = getCartByUserId(userId)
        cart.cartItems.removeIf { it.id == cartItemId }
        return cartRepo.save(cart)
    }

    @Transactional
    fun updateCartItem(userId: Long, cartItemId: Long, newQuantity: Int): Cart {
        val cart = getCartByUserId(userId)
        val item = cart.cartItems.find { it.id == cartItemId }
        item?.quantity = newQuantity
        return cartRepo.save(cart)
    }
}
