package com.example.spring_project.core.error

enum class ErrorCode(val code: Int, val defaultMessage: String) {
    RESOURCE_NOT_FOUND(1001, "Resource not found"),
    INVALID_AUTH_CREDENTIALS(1002, "Invalid authentication credentials"),
    UNKNOWN_ERROR(1003, "Unknown error has happened"),
    VALIDATION_ERROR(1004, "There is a validation error"),

}
