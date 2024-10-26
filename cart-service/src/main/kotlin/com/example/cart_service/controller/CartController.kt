package com.example.cart_service.controller

import com.example.cart_service.core.RequestValidator
import com.example.cart_service.core.dto.CustomResponse
import com.example.cart_service.dto.CartRequest
import com.example.cart_service.dto.CartResponse
import com.example.cart_service.model.toCartResponse
import com.example.cart_service.service.CartService
import com.example.cart_service.service.SkuService
import com.example.cart_service.service.TokenValidationService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cart")
class CartController(
    private val cartService: CartService,
    private val validator: RequestValidator,
    private val tokenValidationService : TokenValidationService,
    private val skuService: SkuService
) {

    @GetMapping("/")
    fun getCartByUserId(
        @RequestHeader("Authorization") token: String
    ): CustomResponse< CartResponse> {

        return tokenValidationService.tokenValidation(token) {
            val cart = cartService.getCartByUserId(it?.data?.tokenData?.userId!!.toLong()).toCartResponse()

            val skus = skuService.getSkusById(cart.cartItems.map { it.skuId })

            cart.cartItems.forEachIndexed { index, cartItem ->
                cartItem.sku = skus?.data?.get(index)
            }

            CustomResponse(
                isSuccessful = true,
                message = "Successfully retrieved cart for user ID: ${it.data.tokenData.userId}",
                data = cart
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
            ).toCartResponse()

            val skus = skuService.getSkusById(cart.cartItems.map { it.skuId })

            cart.cartItems.forEachIndexed { index, cartItem ->
                cartItem.sku = skus?.data?.get(index)
            }

            CustomResponse(
                isSuccessful = true,
                message = "Successfully added item to cart for user ID: ${it.data.tokenData.userId}",
                data = cart
            )

        }

    }

    @DeleteMapping("/{cartItemId}")
    fun removeCartItem(
        @RequestHeader("Authorization") token: String,
        @PathVariable cartItemId: Long
    ): CustomResponse<CartResponse> {
        return tokenValidationService.tokenValidation(token) {

            val cart = cartService.removeCartItem(it?.data?.tokenData!!.userId.toLong(), cartItemId).toCartResponse()

            val skus = skuService.getSkusById(cart.cartItems.map { it.skuId })

            cart.cartItems.forEachIndexed { index, cartItem ->
                cartItem.sku = skus?.data?.get(index)
            }

            CustomResponse(
                isSuccessful = true,
                message = "Successfully removed item from cart for user ID: ${it.data.tokenData.userId}",
                data = cart
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

            val cart = cartService.updateCartItem(it?.data?.tokenData!!.userId.toLong(), cartItemId, quantity).toCartResponse()

            val skus = skuService.getSkusById(cart.cartItems.map { it.skuId })

            cart.cartItems.forEachIndexed { index, cartItem ->
                cartItem.sku = skus?.data?.get(index)
            }

            CustomResponse(
                isSuccessful = true,
                message = "Successfully updated item in cart for user ID: ${it.data.tokenData.userId}",
                data = cart
            )
        }
    }

}
