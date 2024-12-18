package com.example.auth_service.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*


@Service
class JwtService {

    @Value("\${security.jwt.secret-key}")
    private lateinit var secretKey: String

    @Value("\${security.jwt.expiration-time}")
    private var jwtExpiration: Long = 0

    fun extractUsername(token: String): String? {
        return extractClaim(token) { claims -> claims.subject }
    }

    fun extractUserId(token: String): Long? {
        return extractClaim(token) { claims -> claims["userId"] as Long }
    }

    fun extractUserRoles(token: String): List<String>? {
        return extractClaim(token) { claims -> claims["roles"] as List<String> }
    }

    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims: Claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    fun extractAllClaims(token: String): Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun getSignInKey(): Key {
        val keyBytes: ByteArray = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    // Modify this method to include user ID and roles in the token
    fun generateToken(userDetails: UserDetails, userId: Long, roles: List<String>): String {
        val extraClaims: MutableMap<String, Any> = mutableMapOf(
            "userId" to userId,
            "roles" to roles
        )
        return buildToken(extraClaims, userDetails, jwtExpiration)
    }

    private fun buildToken(extraClaims: MutableMap<String, Any>, userDetails: UserDetails, jwtExpiration: Long): String {
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + jwtExpiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        return extractUsername(token) == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }
}