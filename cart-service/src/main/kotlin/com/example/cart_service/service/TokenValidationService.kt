package com.example.cart_service.service

import com.example.cart_service.core.dto.CustomError
import com.example.cart_service.core.dto.CustomResponse
import com.example.cart_service.core.error.ErrorCode
import com.example.cart_service.dto.VerifyTokenResponse
import com.example.cart_service.model.toCartResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class TokenValidationService(
    @Autowired private val webClientBuilder: WebClient.Builder
) {

    private fun verifyToken(token: String): VerifyTokenResponse? {
        val webClient = webClientBuilder.baseUrl("http://localhost:8080").build()

        val response = webClient.post()
            .uri("/api/auth/verify-token")
            .bodyValue(mapOf(
                "token" to token
            ))
            .retrieve()
            .bodyToMono(VerifyTokenResponse::class.java)
            .block()

        return response
    }


    fun<T> tokenValidation(token : String ,callBackFunction : (VerifyTokenResponse?)-> CustomResponse<T> ) : CustomResponse<T>{
        val resposne = verifyToken(token)

        return if(resposne?.isSuccessful == true && resposne.data?.verified == true) {

            callBackFunction(resposne)

        }else {
            CustomResponse(
                isSuccessful = false,
                message = "The token you provided is not a valid token",
                data = null,
                error = CustomError(
                    errorCode = ErrorCode.VALIDATION_ERROR.code,
                    message = resposne?.error?.message ?: ErrorCode.VALIDATION_ERROR.defaultMessage,
                )
            )
        }

    }


}
