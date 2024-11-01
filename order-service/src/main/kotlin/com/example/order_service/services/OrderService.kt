package com.example.order_service.services


import com.example.order_service.core.error.CustomException
import com.example.order_service.core.error.ErrorCode
import com.example.order_service.dto.OrderRequest
import com.example.order_service.models.Order
import com.example.order_service.models.OrderItem
import com.example.order_service.models.OrderStatus
import com.example.order_service.repos.OrderItemRepo
import com.example.order_service.repos.OrderRepo
import org.springframework.stereotype.Service


@Service
class OrderService(
    val cartService : CartService,
    val orderRepo: OrderRepo,
    val orderItemRepo: OrderItemRepo
) {

    fun createOrder(userId: Long, token: String, orderRequest: OrderRequest): Order {
        val cart = cartService.getCartByUserId(token)

        // Map cart items to OrderItems
        val items = cart?.data?.cartItems?.map { cartItem ->
            OrderItem(
                productId = cartItem.productId,
                quantity = cartItem.quantity,
                price = cartItem.sku.price,
                skuId = cartItem.sku.id
            )
        }?.toMutableList() ?: mutableListOf()

        // Create Order object without items initially
        val order = Order(
            userId = userId,
            totalAmount = items.sumOfTotal(),
            streetAddress = orderRequest.streetAddress,
            city = orderRequest.city,
            state = orderRequest.state,
            postalCode = orderRequest.postalCode,
            country = orderRequest.country,
            contactName = orderRequest.contactName,
            contactNumber = orderRequest.contactNumber
        )

        // Associate each OrderItem with the Order
        items.forEach { it.order = order }

        // Save the Order, then save the associated items
        orderRepo.save(order)
        orderItemRepo.saveAll(items)

        // Clear the user's cart
        cartService.clearCartByUserId(token)

        return order
    }


    fun getOrderByUserId(userId : Long) : List<Order> {
        return orderRepo.findAllByUserId(userId)
    }


    fun updateOrder(id : Long, orderStatus : OrderStatus) : Order {
        val order = orderRepo.findById(id)

        if(order.isPresent) order.get().status = orderStatus else throw CustomException.create(ErrorCode.RESOURCE_NOT_FOUND)
        return orderRepo.save(order.get())
    }


}

fun List<OrderItem>.sumOfTotal(): Double {
    var total : Double = 0.0
    this.forEach { item ->
        total += ((item.price?: 0.0) * (item.quantity?.toDouble()?: 0.0))
    }
    return total
}

