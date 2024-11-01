package com.example.order_service.repos

import com.example.order_service.models.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepo : JpaRepository<Order,Long> {

    fun findAllByUserId(userId : Long) : List<Order>

}