package com.simkord.fridgepalbackend.application.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {
    @Value("\${jwt.secret}")
    private lateinit var secretString: String

    private val key by lazy {
        Keys.hmacShaKeyFor(secretString.toByteArray(Charsets.UTF_8))
    }
    private val expiration = 3600000

    fun generateToken(auth: Authentication): String {
        val authorities = auth.authorities.map { it.authority }
        return Jwts.builder()
            .setSubject(auth.name)
            .claim("authorities", authorities)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(key)
            .compact()
    }

    fun extractUsername(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }
}
