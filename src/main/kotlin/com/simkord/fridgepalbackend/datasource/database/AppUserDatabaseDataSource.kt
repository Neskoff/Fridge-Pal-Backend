package com.simkord.fridgepalbackend.datasource.database

import com.github.michaelbull.result.*
import com.simkord.fridgepalbackend.datasource.AppUserDataSource
import com.simkord.fridgepalbackend.datasource.database.entity.AppUserEntity
import com.simkord.fridgepalbackend.datasource.database.jpa.AppUserJpaRepository
import com.simkord.fridgepalbackend.datasource.database.mapper.toDatasourceResult
import com.simkord.fridgepalbackend.datasource.database.model.DatasourceError
import org.springframework.stereotype.Component

@Component
class AppUserDatabaseDataSource(
    private val appUserJpaRepository: AppUserJpaRepository,
) : AppUserDataSource {

    override fun loadUserByUsername(username: String): Result<AppUserEntity, DatasourceError> {
        return runCatching { appUserJpaRepository.findByUsername(username).get() }.toDatasourceResult()
    }

    override fun saveUser(user: AppUserEntity): Result<AppUserEntity, DatasourceError> {
        return runCatching { appUserJpaRepository.save(user) }.toDatasourceResult()
    }

    override fun existsByUsername(username: String): Result<Boolean, DatasourceError> {
        return runCatching { appUserJpaRepository.existsByUsername(username) }.toDatasourceResult()
    }
}
