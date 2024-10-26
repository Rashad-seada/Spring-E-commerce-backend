package com.example.cart_service.service

import com.example.cart_service.core.dto.CustomResponse
import com.example.cart_service.dto.CustomSkuResponse
import com.example.cart_service.dto.SkuResponse
import com.example.cart_service.dto.VerifyTokenResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient


@Service
class SkuService @Autowired constructor(
    @Autowired private val webClientBuilder: WebClient.Builder
){


    fun getSkusById(ids: List<Long?>): CustomSkuResponse? {
        val webClient = webClientBuilder.baseUrl("http://localhost:8080").build()

        val response = webClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("/api/sku/ids")
                    .queryParam("ids", ids.filterIsInstance<Long>().joinToString(","))
                    .build()
            }
            .retrieve()
            .bodyToMono(CustomSkuResponse::class.java)
            .block()

        return response
    }


}