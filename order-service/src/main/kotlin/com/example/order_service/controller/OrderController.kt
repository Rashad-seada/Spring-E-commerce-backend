package com.example.order_service.controller

import com.example.order_service.core.RequestValidator.validateRequest
import com.example.order_service.core.dto.CustomResponse
import com.example.order_service.dto.OrderRequest
import com.example.order_service.models.Order
import com.example.order_service.services.OrderService
import com.example.order_service.services.TokenValidationService
import jakarta.validation.Valid
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/order")
class OrderController(
    val orderService: OrderService,
    val tokenValidationService: TokenValidationService,

) {

    @PostMapping("/")
    fun createOrder(
        @RequestHeader("Authorization") token: String,
        @Valid @RequestBody orderRequest: OrderRequest,
        bindingResult: BindingResult

    ) : CustomResponse<Order> {
        val error = validateRequest<Order>(bindingResult)
        if(error != null) return error

        return tokenValidationService.tokenValidation(token){

             CustomResponse(
                isSuccessful = true,
                message = "The order is created successfully",
                data =  orderService.createOrder(it?.data?.tokenData?.userId!!.toLong(),token, orderRequest)
            )
        }

    }

    @GetMapping("/")
    fun getOrders(
        @RequestHeader("Authorization") token: String,
    ) : CustomResponse<List<Order>> {
        return tokenValidationService.tokenValidation(token){

            CustomResponse(
                isSuccessful = true,
                message = "Got the orders successfully",
                data =  orderService.getOrderByUserId(it?.data?.tokenData?.userId!!.toLong())
            )
        }
    }

}