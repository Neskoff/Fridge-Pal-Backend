package com.simkord.fridgepalbackend.application.response

import com.simkord.fridgepalbackend.service.enums.UserRole

data class AppUserResponse(
    val username: String,
    val password: String,
    val role: UserRole,
)
