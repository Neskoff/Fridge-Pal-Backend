package com.simkord.fridgepalbackend.service

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.simkord.fridgepalbackend.datasource.AppUserDataSource
import com.simkord.fridgepalbackend.service.enums.UserRole
import com.simkord.fridgepalbackend.service.mapper.*
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
        val user = appUserDataSource.loadUserByUsername(username).mapWithException { it }

        if (!user.isPresent) {
            throw BadCredentialsException(INVALID_USER_CREDENTIALS)
        }
        return user.get().toAppUser()
    }

    fun registerUser(username: String, password: String): Result<AppUser, ServiceError> {
        val userExists = appUserDataSource.existsByUsername(username).mapWithException { it }

        if (userExists) {
            return Err(ServiceError(HttpStatus.CONFLICT.value(), "User $username Already Exists"))
        }

        val user = AppUser(
            userUsername = username,
            userPassword = password,
            userRole = UserRole.USER,
        )

        return appUserDataSource.saveUser(user.toAppUserEntity()).mapToServiceResult { it.toAppUser() }
    }

    companion object {
        private const val INVALID_USER_CREDENTIALS = "Bad credentials"
    }
}
