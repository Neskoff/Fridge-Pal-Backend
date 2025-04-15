package com.simkord.fridgepalbackend.service

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.fold
import com.simkord.fridgepalbackend.application.exception.FridgePalException
import com.simkord.fridgepalbackend.datasource.AppUserDataSource
import com.simkord.fridgepalbackend.service.enums.UserRole
import com.simkord.fridgepalbackend.service.mapper.toAppUser
import com.simkord.fridgepalbackend.service.mapper.toAppUserEntity
import com.simkord.fridgepalbackend.service.mapper.toServiceError
import com.simkord.fridgepalbackend.service.model.AppUser
import com.simkord.fridgepalbackend.service.model.ServiceError
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AppUserService(
    private val appUserDataSource: AppUserDataSource,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): AppUser {
        val user = appUserDataSource.loadUserByUsername(username).fold(
            success = { it },
            failure = {
                if (it.errorCode == HttpStatus.NOT_FOUND.value()) {
                    throw BadCredentialsException("Invalid user credentials")
                }
                throw FridgePalException(HttpStatus.valueOf(it.errorCode), it.errorMessage)
            },
        )
        return user.toAppUser()
    }

    fun registerUser(username: String, password: String): Result<AppUser, ServiceError> {
        val userExists = appUserDataSource.existsByUsername(username).fold(
            success = { it },
            failure = { return@registerUser Err(it.toServiceError()) },
        )

        if (userExists) {
            return Err(ServiceError(HttpStatus.CONFLICT.value(), "User $username Already Exists"))
        }

        val user = AppUser(
            userUsername = username,
            userPassword = password,
            userRole = UserRole.USER,
        )

        return appUserDataSource.saveUser(user.toAppUserEntity()).fold(
            success = { Ok(it.toAppUser()) },
            failure = { Err(it.toServiceError()) },
        )
    }
}
