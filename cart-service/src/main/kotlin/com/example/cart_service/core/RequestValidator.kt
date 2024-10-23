package com.example.cart_service.core

import com.example.cart_service.core.dto.CustomError
import com.example.cart_service.core.dto.CustomResponse
import com.example.cart_service.core.error.ErrorCode
import org.springframework.validation.BindingResult

object RequestValidator {

    fun <T> validateRequest(bindingResult: BindingResult) : CustomResponse<T>?{
        if (bindingResult.hasErrors()) {
            val errorMessages = bindingResult.allErrors.map { it.defaultMessage ?: "Invalid field" }
            return CustomResponse(
                isSuccessful = false,
                message = ErrorCode.VALIDATION_ERROR.defaultMessage,
                error = CustomError(
                    errorCode = ErrorCode.VALIDATION_ERROR.code,
                    message = errorMessages.first()
                )
            )
        }
        return null
    }

}