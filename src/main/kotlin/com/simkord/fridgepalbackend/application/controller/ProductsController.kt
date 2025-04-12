package com.simkord.fridgepalbackend.application.controller

import com.simkord.fridgepalbackend.application.response.ProductResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "Product Controller", description = "Retrieve, add, and modify existing products in the fridge")
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
                        mediaType = "application/json",
                        schema = Schema(
                            type = "array",
                            implementation = ProductResponse::class,
                            example = "[{\"name\":\"Bananas\",\"type\":\"Fruit\",\"quantity\":1.5,\"quantityUnit\":\"kilograms\",\"storedDate\":\"2025-04-12\",\"expiryDate\":\"2025-04-19\"}]",
                        ),
                    ),
                ],
            ),
        ],
    )
    fun getProducts(): ResponseEntity<MutableList<ProductResponse>>
}
