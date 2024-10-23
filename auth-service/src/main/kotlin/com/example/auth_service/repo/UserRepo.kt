package com.example.auth_service.repo

import com.example.auth_service.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo  : JpaRepository<User, Long> {

    fun findByEmail(email: String): User?

    fun findByUsername(username: String): User?

    fun findByVerificationCode(verificationCode: String): User?

}