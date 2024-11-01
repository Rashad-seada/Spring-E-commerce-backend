package com.example.order_service.repos

import com.example.order_service.models.OrderItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface OrderItemRepo : JpaRepository<OrderItem,Long> {
}