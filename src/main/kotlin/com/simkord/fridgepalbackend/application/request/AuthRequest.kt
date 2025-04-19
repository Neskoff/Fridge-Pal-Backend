package com.simkord.fridgepalbackend.application.request

data class AuthRequest(
    val username: String,
    val password: String,
)
