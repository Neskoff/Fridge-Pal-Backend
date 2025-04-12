package com.simkord.fridgepalbackend.application.controller

import com.simkord.fridgepalbackend.application.exception.FridgePalException
import com.simkord.fridgepalbackend.application.response.ApiErrorResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.OffsetDateTime

@ControllerAdvice
class ApiErrorHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(FridgePalException::class)
    fun handleException(
        ex: FridgePalException,
        request: HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        val responseBody = ApiErrorResponse(
            timestamp = OffsetDateTime.now(),
            status = ex.errorCode.value(),
            error = ex.errorCode.name,
            message = ex.message,
            path = request.requestURI,
        )
        return ResponseEntity<ApiErrorResponse>(responseBody, ex.errorCode)
    }
}
