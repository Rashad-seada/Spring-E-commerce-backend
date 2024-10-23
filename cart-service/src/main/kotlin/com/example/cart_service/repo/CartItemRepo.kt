package com.example.cart_service.repo

import com.example.cart_service.model.CartItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartItemRepo : JpaRepository<CartItem, Long>
