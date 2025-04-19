package com.simkord.fridgepalbackend.datasource.database.mapper

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.fold
import com.simkord.fridgepalbackend.datasource.database.model.DatasourceError
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus

private val logger = KotlinLogging.logger {}

fun <T, R> Result<T, R>.toDatasourceResult(
    errorCode: Int = HttpStatus.INTERNAL_SERVER_ERROR.value(),
    errorMessage: String = UNEXPECTED_DATABASE_ERROR_MESSAGE,
): Result<T, DatasourceError> {
    return this.fold(
        success = { Ok(it) },
        failure = {
            logger.error { it }
            Err(DatasourceError(errorCode, errorMessage))
        },
    )
}

private const val UNEXPECTED_DATABASE_ERROR_MESSAGE = "Unexpected database error"
