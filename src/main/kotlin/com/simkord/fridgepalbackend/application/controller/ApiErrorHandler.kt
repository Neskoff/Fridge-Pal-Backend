package com.simkord.fridgepalbackend.application.controller

import com.simkord.fridgepalbackend.application.exception.FridgePalException
import com.simkord.fridgepalbackend.application.response.ApiErrorResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.*
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
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

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any> {
        val errors = ex.bindingResult.fieldErrors.associate { it.field to (it.defaultMessage ?: "Invalid value") }

        val body = mapOf(
            "status" to HttpStatus.BAD_REQUEST.value(),
            "error" to "Validation Failed",
            "message" to errors,
            "path" to (request.getDescription(false).removePrefix("uri=")),
        )

        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }
}
