package com.simkord.fridgepalbackend.application.controller

import com.simkord.fridgepalbackend.application.exception.FridgePalException
import com.simkord.fridgepalbackend.application.mapper.toAppUserResponse
import com.simkord.fridgepalbackend.application.mapper.toMappedResponseEntity
import com.simkord.fridgepalbackend.application.request.AuthRequest
import com.simkord.fridgepalbackend.application.response.AppUserResponse
import com.simkord.fridgepalbackend.application.response.TokenResponse
import com.simkord.fridgepalbackend.application.security.JwtUtil
import com.simkord.fridgepalbackend.service.AppUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
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
        return runCatching {
            val authResponse = authenticationManager.authenticate(authToken)
            ResponseEntity(TokenResponse(jwtUtil.generateToken(authResponse)), HttpStatus.OK)
        }.getOrElse { exception ->
            when {
                exception is BadCredentialsException || exception.cause is BadCredentialsException ->
                    throw FridgePalException(HttpStatus.UNAUTHORIZED, exception.message)
                else -> throw exception
            }
        }
    }

    @PostMapping("/register")
    override fun register(@RequestBody request: AuthRequest): ResponseEntity<AppUserResponse> {
        val encodedPassword = passwordEncoder.encode(request.password)

        return appUserService.registerUser(request.username, encodedPassword).toMappedResponseEntity(
            transform = { it.toAppUserResponse() },
            successStatus = HttpStatus.CREATED,
        )
    }
}
