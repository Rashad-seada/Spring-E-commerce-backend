package com.example.auth_service.core.error

class CustomException(val errorCode: ErrorCode) : RuntimeException(errorCode.defaultMessage) {
    companion object {
        fun create(errorCode: ErrorCode): CustomException {
            return CustomException(errorCode)
        }
    }
}
