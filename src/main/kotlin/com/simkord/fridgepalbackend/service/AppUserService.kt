package com.simkord.fridgepalbackend.service

import com.github.michaelbull.result.fold
import com.simkord.fridgepalbackend.application.exception.FridgePalException
import com.simkord.fridgepalbackend.datasource.AppUserDataSource
import com.simkord.fridgepalbackend.service.mapper.toAppUser
import com.simkord.fridgepalbackend.service.model.AppUser
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AppUserService(
    private val appUserDataSource: AppUserDataSource
) : UserDetailsService {

    override fun loadUserByUsername(username: String): AppUser {
        val user = appUserDataSource.loadUserByUsername(username).fold(
            success = {it},
            failure = {throw FridgePalException(HttpStatus.valueOf(it.errorCode), it.errorMessage)}
        )
        return user.toAppUser()
    }
}
