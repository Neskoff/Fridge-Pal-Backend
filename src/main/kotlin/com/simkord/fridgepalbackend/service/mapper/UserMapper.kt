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

fun AppUser.toAppUserEntity(): AppUserEntity {
    val user = AppUserEntity()
    user.username = username
    user.password = password
    user.role = userRole

    return user
}
