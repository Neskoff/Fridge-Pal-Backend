package com.simkord.fridgepalbackend.application.controller

import com.simkord.fridgepalbackend.application.AuthRequest
import com.simkord.fridgepalbackend.application.security.JwtUtil
import com.simkord.fridgepalbackend.datasource.database.entity.AppUserEntity
import com.simkord.fridgepalbackend.datasource.database.jpa.AppUserJpaRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil,
    private val passwordEncoder: PasswordEncoder,
    private val appUserJpaRepository: AppUserJpaRepository,
) {

    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequest): String {
        val authToken = UsernamePasswordAuthenticationToken(request.username, request.password)
        authenticationManager.authenticate(authToken)
        return jwtUtil.generateToken(request.username)
    }
    @PostMapping("/register")
    fun register(@RequestBody request: AuthRequest): ResponseEntity<String> {
        val encodedPassword = passwordEncoder.encode(request.password)

        val user = AppUserEntity()
        user.password = encodedPassword
        user.username = request.username
        user.role = "USER"
        appUserJpaRepository.save(user)

        return ResponseEntity("User registered successfully", HttpStatus.CREATED)
    }
}


