package com.simkord.fridgepalbackend.application.mapper

import com.simkord.fridgepalbackend.application.response.AppUserResponse
import com.simkord.fridgepalbackend.service.model.AppUser

fun AppUser.toAppUserResponse(): AppUserResponse {
    return AppUserResponse(
        username = userUsername,
        password = userPassword,
        role = userRole,
    )
}
