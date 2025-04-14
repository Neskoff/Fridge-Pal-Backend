package com.simkord.fridgepalbackend.datasource.database

import com.github.michaelbull.result.*
import com.simkord.fridgepalbackend.datasource.AppUserDataSource
import com.simkord.fridgepalbackend.datasource.database.entity.AppUserEntity
import com.simkord.fridgepalbackend.datasource.database.jpa.AppUserJpaRepository
import com.simkord.fridgepalbackend.datasource.database.model.DatasourceError
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class AppUserDatabaseDataSource(
    private val appUserJpaRepository: AppUserJpaRepository,
) : AppUserDataSource {

    override fun loadUserByUsername(username: String): Result<AppUserEntity, DatasourceError> {
        val user = runCatching { appUserJpaRepository.findByUsername(username) }.fold(
            success = { it },
            failure = { return@loadUserByUsername Err(DatasourceError(HttpStatus.INTERNAL_SERVER_ERROR.value(), it.message)) },
        )
        if (!user.isPresent) {
            return Err(DatasourceError(HttpStatus.NOT_FOUND.value(), "User $username not found"))
        }
        return Ok(user.get())
    }

    override fun saveUser(user: AppUserEntity): Result<AppUserEntity, DatasourceError> {
        return runCatching { appUserJpaRepository.save(user) }.fold(
            success = { Ok(it) },
            failure = { Err(DatasourceError(HttpStatus.INTERNAL_SERVER_ERROR.value(), it.message)) },
        )
    }

    override fun existsByUsername(username: String): Result<Boolean, DatasourceError> {
        return runCatching { appUserJpaRepository.existsByUsername(username) }.fold(
            success = { Ok(it) },
            failure = { Err(DatasourceError(HttpStatus.INTERNAL_SERVER_ERROR.value(), it.message)) },
        )
    }
}
