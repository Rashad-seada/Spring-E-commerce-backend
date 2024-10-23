package com.example.auth_service.service

import com.example.auth_service.core.error.CustomException
import com.example.auth_service.core.error.ErrorCode
import com.example.auth_service.dto.LoginUserDto
import com.example.auth_service.dto.RegisterUserDto
import com.example.auth_service.dto.VerifyUserDto
import com.example.auth_service.model.User
import com.example.auth_service.repo.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.random.Random


@Service
class AuthService @Autowired constructor(
    val userRepo: UserRepo,
    val passwordEncoder: PasswordEncoder,
    val emailService: EmailService,
    val authenticationManager : AuthenticationManager
) {


    fun login(loginUserDto: LoginUserDto): User {

        try {
            val user = userRepo.findByEmail(loginUserDto.email ?: throw IllegalArgumentException("email is required"))
                ?: throw CustomException.create(ErrorCode.RESOURCE_NOT_FOUND)

            if(!user.isEnabled) {

                if(user.verificationCodeExpiresAt != null
                    && user.verificationCodeExpiresAt!!.isBefore(LocalDateTime.now())) {
                    sendVerificationEmail(user)
                }

                throw CustomException.create(ErrorCode.ACCOUNT_NOT_VERIFIED)
            }

            println("Authenticating user with email: ${loginUserDto.email} and password: ${loginUserDto.password}")

            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    loginUserDto.email,
                    loginUserDto.password
                )
            )

            println("Authentication fished")


            return user
        } catch (e : Exception){
            println("Error during authentication: ${e.message}")
            throw RuntimeException(e.message)
        }

    }

    fun register(registerUserDto: RegisterUserDto): User {

        val user = userRepo.findByEmail(registerUserDto.email ?: throw IllegalArgumentException("email is required"))

        if(user != null) {
            throw CustomException.create(ErrorCode.RESOURCE_ALREADY_FOUND)
        }

        val newUser = User(
            username = registerUserDto.username ?: throw IllegalArgumentException("username is required"),
            email = registerUserDto.email,
            password = passwordEncoder.encode(registerUserDto.password),
            isEnabled = false,
            verificationCode =  generateVerificationCode(),
            verificationCodeExpiresAt = LocalDateTime.now().plusMinutes(15)
        )
        sendVerificationEmail(newUser)
        return userRepo.save(newUser)
    }

    fun verifyUser(verifyUserDto: VerifyUserDto): User {

        val user = userRepo.findByEmail(verifyUserDto.email ?: throw IllegalArgumentException("email is required"))
            ?: throw CustomException.create(ErrorCode.RESOURCE_ALREADY_FOUND)

        if(user.verificationCodeExpiresAt != null
            && user.verificationCodeExpiresAt!!.isBefore(LocalDateTime.now())) {

            throw CustomException.create(ErrorCode.VERIFICATION_CODE_EXPIRED)
        }

        if(user.verificationCode != null
            && user.verificationCode == verifyUserDto.verificationCode
        ) {
            val enabledUser = user.copy(
                isEnabled = true,
                verificationCode = null,
                verificationCodeExpiresAt = null
            )

            return userRepo.save(enabledUser)

        } else {
            throw CustomException.create(ErrorCode.INVALID_AUTH_CREDENTIALS)
        }

    }


    fun resendVerificationCode(email : String?) {
        val user = userRepo.findByEmail(email ?: throw IllegalArgumentException("email is required"))
            ?: throw CustomException(ErrorCode.RESOURCE_NOT_FOUND)

        if(user.isEnabled) throw CustomException(ErrorCode.ACCOUNT_IS_VERIFIED)

        val newUser = user.copy(
            verificationCode = generateVerificationCode(),
            verificationCodeExpiresAt = LocalDateTime.now().plusMinutes(15)
        )

        userRepo.save(newUser)

        sendVerificationEmail(newUser)


    }

    private fun generateVerificationCode(): String {
        return (Random.nextInt(900000) + 100000).toString()
    }

    private fun sendVerificationEmail(user : User) {
        val subject = "Account verification"
        val verificationCode = user.verificationCode
        val htmlMessage = (("<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode) + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>")

        try {

            emailService.sendVerificationEmail(
                to = user.email,
                subject = subject,
                text = htmlMessage
            )

        } catch (exception : Exception) {
            throw RuntimeException(exception.message)
        }

    }


}