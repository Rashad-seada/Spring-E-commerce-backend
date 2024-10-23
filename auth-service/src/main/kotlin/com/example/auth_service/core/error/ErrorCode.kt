package com.example.auth_service.core.error

enum class ErrorCode(val code: Int, val defaultMessage: String) {
    RESOURCE_NOT_FOUND(1001, "email not found"),
    INVALID_AUTH_CREDENTIALS(1002, "Invalid authentication credentials"),
    UNKNOWN_ERROR(1003, "Unknown error has happened"),
    VALIDATION_ERROR(1004, "There is a validation error"),
    ACCOUNT_NOT_VERIFIED(1005, "The account is not verified. Please verify your account"),
    ACCOUNT_IS_VERIFIED(1006, "The account is already verified"),
    VERIFICATION_CODE_EXPIRED(1007, "The verification code is expired"),
    RESOURCE_ALREADY_FOUND(1008, "the email already found"),


}
