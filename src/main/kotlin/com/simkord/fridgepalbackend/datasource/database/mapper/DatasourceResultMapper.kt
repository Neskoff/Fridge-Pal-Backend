package com.simkord.fridgepalbackend.datasource.database.mapper

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.fold
import com.simkord.fridgepalbackend.datasource.database.model.DatasourceError
import org.springframework.http.HttpStatus

fun <T, R> Result<T, R>.toDatasourceResult(
    errorCode: Int = HttpStatus.INTERNAL_SERVER_ERROR.value(),
    errorMessage: String = UNEXPECTED_DATABASE_ERROR_MESSAGE,
): Result<T, DatasourceError> {
    return this.fold(
        success = { Ok(it) },
        failure = { Err(DatasourceError(errorCode, errorMessage)) },
    )
}

private const val UNEXPECTED_DATABASE_ERROR_MESSAGE = "Unexpected database error"
