package com.simkord.fridgepalbackend.datasource

import com.github.michaelbull.result.Result
import com.simkord.fridgepalbackend.datasource.database.entity.AppUserEntity
import com.simkord.fridgepalbackend.datasource.database.model.DatasourceError
import java.util.*

interface AppUserDataSource {
    fun loadUserByUsername(username: String): Result<Optional<AppUserEntity>, DatasourceError>

    fun saveUser(user: AppUserEntity): Result<AppUserEntity, DatasourceError>

    fun existsByUsername(username: String): Result<Boolean, DatasourceError>
}
