package com.example.cart_service.repo

import com.example.cart_service.model.Cart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepo : JpaRepository<Cart, Long> {
    fun findByUserId(userId: Long): Cart?
}
