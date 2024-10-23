package com.example.cart_service.controller

import com.example.cart_service.core.RequestValidator
import com.example.cart_service.core.dto.CustomError
import com.example.cart_service.core.dto.CustomResponse
import com.example.cart_service.core.error.CustomException
import com.example.cart_service.core.error.ErrorCode
import com.example.cart_service.dto.CartRequest
import com.example.cart_service.dto.CartResponse
import com.example.cart_service.model.toCartResponse
import com.example.cart_service.service.CartService
import com.example.cart_service.service.TokenValidationService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cart")
class CartController(
    private val cartService: CartService,
    private val validator: RequestValidator,
    private val tokenValidationService : TokenValidationService
) {

    @GetMapping("/")
    fun getCartByUserId(
        @RequestHeader("Authorization") token: String
    ): CustomResponse<CartResponse> {

        return tokenValidationService.tokenValidation(token) {
            val cart = cartService.getCartByUserId(it?.data?.tokenData?.userId!!.toLong())

            CustomResponse(
                isSuccessful = true,
                message = "Successfully retrieved cart for user ID: ${it.data.tokenData.userId}",
                data = cart.toCartResponse()
            )
        }

    }

    @PostMapping("/")
    fun addCartItem(
        @RequestHeader("Authorization") token: String,
        @Valid @RequestBody cartRequest : CartRequest,
    ): CustomResponse<CartResponse> {

        return tokenValidationService.tokenValidation(token) {

            val cart = cartService.addCartItem(
                it?.data?.tokenData!!.userId.toLong(),
                cartRequest.productId,
                cartRequest.skuId,
                cartRequest.quantity
            )
            CustomResponse(
                isSuccessful = true,
                message = "Successfully added item to cart for user ID: ${it.data.tokenData.userId}",
                data = cart.toCartResponse()
            )

        }

    }

    @DeleteMapping("/{cartItemId}")
    fun removeCartItem(
        @RequestHeader("Authorization") token: String,
        @PathVariable cartItemId: Long
    ): CustomResponse<CartResponse> {
        return tokenValidationService.tokenValidation(token) {
            val cart = cartService.removeCartItem(it?.data?.tokenData!!.userId.toLong(), cartItemId)
            CustomResponse(
                isSuccessful = true,
                message = "Successfully removed item from cart for user ID: ${it.data.tokenData.userId}",
                data = cart.toCartResponse()
            )
            }
        }

    @PutMapping("/{cartItemId}")
    fun updateCartItem(
        @RequestHeader("Authorization") token: String,
        @PathVariable cartItemId: Long,
        @RequestParam quantity: Int
    ): CustomResponse<CartResponse> {

        return tokenValidationService.tokenValidation(token) {

            val cart = cartService.updateCartItem(it?.data?.tokenData!!.userId.toLong(), cartItemId, quantity)
            CustomResponse(
                isSuccessful = true,
                message = "Successfully updated item in cart for user ID: ${it.data.tokenData.userId}",
                data = cart.toCartResponse()
            )
        }
    }

}
