package com.simkord.fridgepalbackend.application.controller

import com.simkord.fridgepalbackend.application.annotation.StandardErrorResponses
import com.simkord.fridgepalbackend.application.request.ProductRequest
import com.simkord.fridgepalbackend.application.response.ProductResponse
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
import org.springframework.web.multipart.MultipartFile

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
        ],
    )
    @StandardErrorResponses
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
        ],
    )
    @StandardErrorResponses
    fun saveProduct(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Product object to create",
            required = true,
            content = [
                Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ProductRequest::class),
                ),
            ],
        )
        @RequestBody productRequest: ProductRequest,
    ): ResponseEntity<ProductResponse>

    @Operation(
        summary = "Delete a product",
        description = "Delete a product in the database",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Product successfully deleted",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                    ),
                ],
            ),
        ],
    )
    @StandardErrorResponses
    fun deleteProduct(productId: Long): ResponseEntity<Unit>

    @Operation(
        summary = "Update a product",
        description = "Update a product in the database and return it in the response",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Product successfully updated",
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
        ],
    )
    @StandardErrorResponses
    fun updateProduct(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Product object to update",
            required = true,
            content = [
                Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ProductRequest::class),
                ),
            ],
        )
        @RequestBody productRequest: ProductRequest,
        productId: Long,
    ): ResponseEntity<ProductResponse>

    @Operation(
        summary = "Update a product image",
        description = "Update a product in image",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Product image successfully updated",
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
        ],
    )
    @StandardErrorResponses
    fun updateProductImage(file: MultipartFile, productId: Long): ResponseEntity<ProductResponse>


    companion object {
        private const val PRODUCT_EXAMPLE = "[{\"name\":\"Bananas\",\"type\":\"Fruits\",\"quantity\":1.5,\"quantityUnit\":\"kilograms\",\"storedDate\":\"2025-04-12\",\"expiryDate\":\"2025-04-19\"}]"
    }
}
