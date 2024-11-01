package com.example.order_service.services

import com.example.order_service.core.dto.CustomResponse
import com.example.order_service.dto.CartResponse
import org.apache.commons.lang.ObjectUtils.Null
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient


@Service
class CartService @Autowired constructor(
    @Autowired private val webClientBuilder: WebClient.Builder
){


    fun getCartByUserId(token: String): CartResponse? {
        val webClient = webClientBuilder.baseUrl("http://localhost:8080").build()

        val response = webClient.get()
            .uri("/api/cart/")
            .header("Authorization", token) // Add the Authorization header here
            .retrieve()
            .bodyToMono(CartResponse::class.java)
            .block()

        return response
    }

    fun clearCartByUserId(token : String) : CustomResponse<*>? {
        val webClient = webClientBuilder.baseUrl("http://localhost:8080").build()

        val response = webClient.delete()
            .uri("/api/cart/clear")
            .header("Authorization", token) // Add the Authorization header here
            .retrieve()
            .bodyToMono(CustomResponse::class.java)
            .block()

        return response
    }


}