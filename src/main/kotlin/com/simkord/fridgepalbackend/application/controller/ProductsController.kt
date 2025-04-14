package com.simkord.fridgepalbackend.application.controller

import com.simkord.fridgepalbackend.application.response.ApiErrorResponse
import com.simkord.fridgepalbackend.application.response.ProductResponse
import com.simkord.fridgepalbackend.service.model.Product
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Product Controller", description = "Retrieve, add, and modify existing products in the fridge")
@SecurityRequirement(name = "bearerAuth")
interface ProductsController {
    @Operation(
        summary = "Retrieve products",
        description = "Return products currently stored in the fridge",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Products successfully retrieved",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(
                            type = "array",
                            implementation = ProductResponse::class,
                            example = PRODUCT_EXAMPLE,
                        ),
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ApiErrorResponse::class),
                        examples = [ExampleObject(EXAMPLE_ERROR)],
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ApiErrorResponse::class),
                        examples = [ExampleObject(EXAMPLE_ERROR)],
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "403",
                description = "Forbidden",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ApiErrorResponse::class),
                        examples = [ExampleObject(EXAMPLE_ERROR)],
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "404",
                description = "Not Found",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ApiErrorResponse::class),
                        examples = [ExampleObject(EXAMPLE_ERROR)],
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ApiErrorResponse::class),
                        examples = [ExampleObject(EXAMPLE_ERROR)],
                    ),
                ],
            ),
        ],
    )
    fun getProducts(): ResponseEntity<MutableList<ProductResponse>>

    @Operation(
        summary = "Save a product",
        description = "Save a product in the database and return it in the response",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Product successfully created",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(
                            implementation = ProductResponse::class,
                            example = PRODUCT_EXAMPLE,
                        ),
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ApiErrorResponse::class),
                        examples = [ExampleObject(EXAMPLE_ERROR)],
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ApiErrorResponse::class),
                        examples = [ExampleObject(EXAMPLE_ERROR)],
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "403",
                description = "Forbidden",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ApiErrorResponse::class),
                        examples = [ExampleObject(EXAMPLE_ERROR)],
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "404",
                description = "Not Found",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ApiErrorResponse::class),
                        examples = [ExampleObject(EXAMPLE_ERROR)],
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ApiErrorResponse::class),
                        examples = [ExampleObject(EXAMPLE_ERROR)],
                    ),
                ],
            ),
        ],
    )
    fun saveProduct(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Product object to create",
            required = true,
            content = [
                Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = Product::class),
                ),
            ],
        )
        @RequestBody productRequest: Product,
    ): ResponseEntity<ProductResponse>

    companion object {
        private const val PRODUCT_EXAMPLE = "[{\"name\":\"Bananas\",\"type\":\"Fruits\",\"quantity\":1.5,\"quantityUnit\":\"kilograms\",\"storedDate\":\"2025-04-12\",\"expiryDate\":\"2025-04-19\"}]"
        private const val EXAMPLE_ERROR = "{\"timestamp\":\"2024-04-14T10:00:00Z\",\"status\":400,\"error\":\"Bad Request\",\"message\":\"Invalid input\",\"path\":\"/api/example\",\"errorCode\":\"INVALID_INPUT\"}"
    }
}
