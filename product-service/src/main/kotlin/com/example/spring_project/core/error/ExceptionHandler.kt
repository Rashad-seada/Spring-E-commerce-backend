package com.example.spring_project.core.error

import com.example.spring_project.core.dto.CustomError
import com.example.spring_project.core.dto.CustomResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(CustomException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleCustomException(exception: CustomException): CustomResponse<Unit> {
        return CustomResponse(
            isSuccessful = false,
            message = exception.message ?: "Error occurred",
            error = CustomError(
                errorCode = exception.errorCode.code,
                message = exception.message ?: "Unknown error occurred"
            )
        )
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGeneralException(exception: Exception): CustomResponse<Unit> {
        return CustomResponse(
            isSuccessful = false,
            message = "An unexpected error occurred",
            error = CustomError(
                errorCode = ErrorCode.UNKNOWN_ERROR.code,
                message = exception.message ?: "Unknown error"
            )
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<CustomResponse<Unit>> {
        val errors = ex.bindingResult.fieldErrors
            .map { fieldError ->
                CustomError(
                    errorCode = ErrorCode.VALIDATION_ERROR.code,
                    message = "${fieldError.field}: ${fieldError.defaultMessage}"
                )
            }

        val response = CustomResponse<Unit>(
            isSuccessful = false,
            message = "Validation failed",
            error = errors.firstOrNull() // Ensure this includes the first error or adjust based on your needs
        )

        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

}
