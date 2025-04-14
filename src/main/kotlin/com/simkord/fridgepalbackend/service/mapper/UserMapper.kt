package com.simkord.fridgepalbackend.service.mapper

import com.simkord.fridgepalbackend.datasource.database.entity.AppUserEntity
import com.simkord.fridgepalbackend.service.model.AppUser

fun AppUserEntity.toAppUser(): AppUser {
    return AppUser(
        userUsername = username,
        userPassword = password,
        userRole = role,
    )
}