package com.example.api_gateway_server.core.error

class CustomException(val errorCode: ErrorCode) : RuntimeException(errorCode.defaultMessage) {
    companion object {
        fun create(errorCode: ErrorCode): CustomException {
            return CustomException(errorCode)
        }
    }
}
