package com.example.auth_service.model

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id : Long? = null,

    @Column(unique = true, nullable = false,)
    private var username : String,

    @Column(unique = true, nullable = false)
    var email : String,

    @Column(unique = false, nullable = false)
    private var password : String,

    @Column(unique = false, nullable = false)
    private var isEnabled : Boolean = false,

    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = true) var role : UserRoles = UserRoles.USER,

    @Column(name = "verification_code",nullable = true)
    var verificationCode : String? = null,

    @Column(name = "verification_expiration",nullable = true)
    var verificationCodeExpiresAt : LocalDateTime? = null,




    ) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf()

    override fun getPassword(): String = this.password

    override fun getUsername(): String = this.username

    override fun isEnabled(): Boolean = this.isEnabled

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

}



enum class UserRoles {
    ADMIN,USER
}
