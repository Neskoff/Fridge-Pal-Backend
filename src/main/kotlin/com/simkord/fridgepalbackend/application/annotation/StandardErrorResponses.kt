package com.simkord.fridgepalbackend.application.annotation

import com.simkord.fridgepalbackend.application.response.ApiErrorResponse
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.MediaType

@ApiResponse(
    responseCode = "400",
    description = "Bad request",
    content = [
        Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = Schema(implementation = ApiErrorResponse::class),
            examples = [ExampleObject(EXAMPLE_ERROR)]
        )
    ]
)
@ApiResponse(
    responseCode = "401",
    description = "Unauthorized",
    content = [
        Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = Schema(implementation = ApiErrorResponse::class),
            examples = [ExampleObject(EXAMPLE_ERROR)]
        )
    ]
)
@ApiResponse(
    responseCode = "403",
    description = "Forbidden",
    content = [
        Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = Schema(implementation = ApiErrorResponse::class),
            examples = [ExampleObject(EXAMPLE_ERROR)]
        )
    ]
)
@ApiResponse(
    responseCode = "404",
    description = "Not Found",
    content = [
        Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = Schema(implementation = ApiErrorResponse::class),
            examples = [ExampleObject(EXAMPLE_ERROR)]
        )
    ]
)
@ApiResponse(
    responseCode = "500",
    description = "Internal Server Error",
    content = [
        Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = Schema(implementation = ApiErrorResponse::class),
            examples = [ExampleObject(EXAMPLE_ERROR)]
        )
    ]
)
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class StandardErrorResponses

private const val EXAMPLE_ERROR = "{\"timestamp\":\"2024-04-14T10:00:00Z\",\"status\":400,\"error\":\"Bad Request\",\"message\":\"Invalid input\",\"path\":\"/api/example\",\"errorCode\":\"INVALID_INPUT\"}"
