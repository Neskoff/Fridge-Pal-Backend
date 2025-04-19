package com.simkord.fridgepalbackend.application.controller

import com.simkord.fridgepalbackend.application.security.JwtAuthFilter
import com.simkord.fridgepalbackend.application.security.JwtUtil
import org.springframework.test.context.bean.override.mockito.MockitoBean

open class NoSecurityTestConfig {
    @MockitoBean
    private lateinit var jwtAuthFilter: JwtAuthFilter

    @MockitoBean
    private lateinit var jwtUtil: JwtUtil
}
