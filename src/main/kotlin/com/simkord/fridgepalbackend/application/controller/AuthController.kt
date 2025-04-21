package com.simkord.fridgepalbackend.application.controller

import com.simkord.fridgepalbackend.application.annotation.StandardErrorResponses
import com.simkord.fridgepalbackend.application.request.AuthRequest
import com.simkord.fridgepalbackend.application.response.AppUserResponse
import com.simkord.fridgepalbackend.application.response.TokenResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Auth Controller", description = "Register new or login as existing user")
interface AuthController {
    @Operation(
        summary = "Login",
        description = "Login as an existing user",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "User successfully logged in",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(
                            implementation = TokenResponse::class,
                            example = TOKEN_EXAMPLE,
                        ),
                    ),
                ],
            ),
        ],
    )
    @StandardErrorResponses
    fun login(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User Request Object",
            required = true,
            content = [
                Content(
                    schema = Schema(implementation = AuthRequest::class),
                ),
            ],
        )
        @RequestBody request: AuthRequest,
    ): ResponseEntity<TokenResponse>

    @Operation(
        summary = "Register",
        description = "Register new user",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "User successfully registered",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(
                            implementation = AppUserResponse::class,
                        ),
                    ),
                ],
            ),
        ],
    )
    @SecurityRequirement(name = "bearerAuth")
    @StandardErrorResponses
    fun register(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User Request Object",
            required = true,
            content = [
                Content(
                    schema = Schema(implementation = AuthRequest::class),
                ),
            ],
        )
        @RequestBody request: AuthRequest,
    ): ResponseEntity<AppUserResponse>

    companion object {
        private const val TOKEN_EXAMPLE = "{\"token\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJpbGlqYSIsImlhdCI6MTc0NDYzODU3MywiZXhwIjoxNzQ0NjQyMTczfQ.MS65GTeSFCUBteva3EfvkwxOY-2eoHmKPGU9ozXcUa0\"}"
    }
}
