package com.simkord.fridgepalbackend.application.mapper

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.fold
import com.simkord.fridgepalbackend.application.exception.FridgePalException
import com.simkord.fridgepalbackend.service.model.ServiceError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

fun <T, R> Result<T, ServiceError>.toMappedResponseEntity(
    transform: (T) -> R,
    successStatus: HttpStatus = HttpStatus.OK,
): ResponseEntity<R> {
    return this.fold(
        success = { ResponseEntity(transform(it), successStatus) },
        failure = { throw FridgePalException(HttpStatus.valueOf(it.errorCode), it.errorMessage) },
    )
}
