package com.simkord.fridgepalbackend.datasource

import com.github.michaelbull.result.Result
import com.simkord.fridgepalbackend.datasource.database.entity.AppUserEntity
import com.simkord.fridgepalbackend.datasource.database.model.DatasourceError

interface AppUserDataSource {
    fun loadUserByUsername(username: String): Result<AppUserEntity, DatasourceError>
}