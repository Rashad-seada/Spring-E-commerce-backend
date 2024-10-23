package com.example.auth_service.controller

import com.example.auth_service.core.dto.CustomError
import com.example.auth_service.core.dto.CustomResponse
import com.example.auth_service.core.error.ErrorCode
import com.example.auth_service.dto.*
import com.example.auth_service.model.User
import com.example.auth_service.model.UserRoles
import com.example.auth_service.repo.UserRepo
import com.example.auth_service.service.AuthService
import com.example.auth_service.service.JwtService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/auth")
@RestController
class AuthController @Autowired constructor(
    val jwtService: JwtService,
    val authService: AuthService,
    val userDetailsService: UserDetailsService,
    val userRepo: UserRepo

) {

    @PostMapping("/register")
    fun register(
        @Valid @RequestBody registerUserDto: RegisterUserDto,
        bindingResult: BindingResult
    ) : CustomResponse<User> {

        val error = validateRequest<User>(bindingResult)
        if(error != null) return error

        val user = authService.register(registerUserDto)
        return CustomResponse(
            isSuccessful = true,
            message = "The user registered successfully, check your email for the verification code",
            data = user
        )

    }

    @PostMapping("/login")
    fun login(
        @Valid @RequestBody loginUserDto: LoginUserDto,
        bindingResult: BindingResult
    ) : CustomResponse<LoginResponse> {

        val error = validateRequest<LoginResponse>(bindingResult)

        if(error != null) return error

        val user = authService.login(loginUserDto)

        return CustomResponse(
            isSuccessful = true,
            message = "The user logged in successfully. Welcome Mr. ${user.username}",
            data = LoginResponse(
                token = jwtService.generateToken(user,user.id!!, listOf(user.role.toString())),
                user = user
            )
        )
    }

    @PostMapping("/send-code")
    fun resendVerificationCode(
        @Valid @RequestBody resendVerificationCodeDto: ResendVerificationCodeDto,
        bindingResult: BindingResult

    ) : CustomResponse<User> {
        val error = validateRequest<User>(bindingResult)
        if(error != null) return error

        authService.resendVerificationCode(resendVerificationCodeDto.email)

        return CustomResponse(
            isSuccessful = true,
            message = "The verification code was sent successfully to" +
                    " ${maskEmail(resendVerificationCodeDto.email 
                        ?: throw IllegalArgumentException("email is required"))}",
            data = null
        )

    }

    @PostMapping("/verify-user")
    fun verifyUser(
        @Valid @RequestBody verifyUserDto: VerifyUserDto,
        bindingResult: BindingResult
    ) : CustomResponse<User> {
        val error = validateRequest<User>(bindingResult)
        if(error != null) return error

         authService.verifyUser(verifyUserDto)

        return CustomResponse(
            isSuccessful = true,
            message = "The account was verified successfully",
            data = null
        )
    }

    @PostMapping("/verify-token")
    fun verifyToken(
        @RequestBody tokenDto: TokenDto,
        bindingResult: BindingResult
    ): CustomResponse<Map<String, Any>> {
        return try {

            val error = validateRequest<Map<String, Any>>(bindingResult)
            if(error != null) return error

            // Extract username from token
            val username = jwtService.extractUsername(tokenDto.token!!)

            // Fetch user details using the username extracted from the token
            val userDetails = userRepo.findByUsername(username ?: throw IllegalArgumentException("Invalid token"))

            // Check if the token is valid using the isTokenValid function
            val isVerified = jwtService.isTokenValid(tokenDto.token, userDetails?: throw IllegalArgumentException("Didn't find user details"))


            CustomResponse(
                isSuccessful = isVerified,
                message = "Token is decoded",
                data = mapOf(
                    "verified" to isVerified,
                    "tokenData" to jwtService.extractAllClaims(tokenDto.token)
                )
            )
        } catch (e: Exception) {
            CustomResponse(
                isSuccessful = false,
                message = "Invalid or expired token",
                data = mapOf("verified" to false),
                error = CustomError(
                    errorCode = 1000,
                    message = e.message.toString()
                )
            )
        }
    }



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

    fun maskEmail(email: String): String {
        val (localPart, domain) = email.split("@")

        val maskedLocalPart = if (localPart.length > 2) {
            localPart.first() + "*".repeat(localPart.length - 2) + localPart.last()
        } else {
            localPart
        }
        return "$maskedLocalPart@$domain"
    }


}