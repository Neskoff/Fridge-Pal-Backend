package com.simkord.fridgepalbackend.application

data class AuthRequest(
    val username: String,
    val password: String
)