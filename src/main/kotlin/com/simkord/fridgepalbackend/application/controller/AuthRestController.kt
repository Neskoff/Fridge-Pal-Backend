package com.simkord.fridgepalbackend.application.controller

import com.github.michaelbull.result.fold
import com.simkord.fridgepalbackend.application.exception.FridgePalException
import com.simkord.fridgepalbackend.application.request.AuthRequest
import com.simkord.fridgepalbackend.application.response.TokenResponse
import com.simkord.fridgepalbackend.application.security.JwtUtil
import com.simkord.fridgepalbackend.service.AppUserService
import com.simkord.fridgepalbackend.service.model.AppUser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthRestController(
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil,
    private val passwordEncoder: PasswordEncoder,
    private val appUserService: AppUserService,
) : AuthController {

    @PostMapping("/login")
    override fun login(@RequestBody request: AuthRequest): ResponseEntity<TokenResponse> {
        val authToken = UsernamePasswordAuthenticationToken(request.username, request.password)
        authenticationManager.authenticate(authToken)
        return ResponseEntity(TokenResponse(jwtUtil.generateToken(request.username)), HttpStatus.OK)
    }

    @PostMapping("/register")
    override fun register(@RequestBody request: AuthRequest): ResponseEntity<AppUser> {
        val encodedPassword = passwordEncoder.encode(request.password)
        val response = appUserService.registerUser(request.username, encodedPassword).fold(
            success = { it },
            failure = { throw FridgePalException(HttpStatus.valueOf(it.errorCode), it.errorMessage) },
        )

        return ResponseEntity(response, HttpStatus.CREATED)
    }
}
