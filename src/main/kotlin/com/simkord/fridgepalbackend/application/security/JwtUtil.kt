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

    fun generateToken(auth: Authentication): String {
        val authorities = auth.authorities.map { it.authority }
        return Jwts.builder()
            .setSubject(auth.name)
            .claim(AUTHORITIES, authorities)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + EXPIRATION))
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

    companion object {
        private const val AUTHORITIES = "authorities"
        private const val EXPIRATION = 3600000
    }
}
