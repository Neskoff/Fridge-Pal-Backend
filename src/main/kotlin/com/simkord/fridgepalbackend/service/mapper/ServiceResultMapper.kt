package com.simkord.fridgepalbackend.service.mapper

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.fold
import com.simkord.fridgepalbackend.application.exception.FridgePalException
import com.simkord.fridgepalbackend.datasource.database.model.DatasourceError
import com.simkord.fridgepalbackend.service.model.ServiceError
import org.springframework.http.HttpStatus

fun <T, R> Result<T, DatasourceError>.mapToServiceResult(transform: (T) -> R): Result<R, ServiceError> {
    return this.fold(
        success = { Ok(transform(it)) },
        failure = { Err(it.toServiceError()) },
    )
}

fun <T, R> Result<T, DatasourceError>.mapWithException(transform: (T) -> R): R {
    return this.fold(
        success = { transform(it) },
        failure = { throw FridgePalException(HttpStatus.valueOf(it.errorCode), it.errorMessage) },
    )
}
