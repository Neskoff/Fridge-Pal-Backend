package com.simkord.fridgepalbackend.datasource.database.model

data class DatasourceError(
    val errorCode: Int,
    val errorMessage: String?,
)
