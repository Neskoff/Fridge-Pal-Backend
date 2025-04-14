package com.simkord.fridgepalbackend.application.controller

import com.github.michaelbull.result.fold
import com.simkord.fridgepalbackend.application.exception.FridgePalException
import com.simkord.fridgepalbackend.application.mapper.toProductResponse
import com.simkord.fridgepalbackend.application.mapper.toProductResponseList
import com.simkord.fridgepalbackend.application.response.ProductResponse
import com.simkord.fridgepalbackend.service.ProductService
import com.simkord.fridgepalbackend.service.model.Product
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/products")
class ProductsRestController(
    private val productService: ProductService,
) : ProductsController {

    @GetMapping
    override fun getProducts(): ResponseEntity<MutableList<ProductResponse>> {
        val response = productService.getProducts().fold(
            success = { ResponseEntity(it.toProductResponseList(), HttpStatus.OK) },
            failure = { throw FridgePalException(HttpStatus.valueOf(it.errorCode), it.errorMessage) },
        )
        return response
    }

    @PostMapping
    override fun saveProduct(productRequest: Product): ResponseEntity<ProductResponse> {
        val response = productService.saveProduct(productRequest).fold(
            success = { ResponseEntity(it.toProductResponse(), HttpStatus.CREATED) },
            failure = { throw FridgePalException(HttpStatus.valueOf(it.errorCode), it.errorMessage) },
        )
        return response
    }

    @DeleteMapping("/{productId}")
    override fun deleteProduct(@PathVariable productId: Long): ResponseEntity<Unit> {
        val response = productService.deleteProduct(productId).fold(
            success = { ResponseEntity(Unit, HttpStatus.NO_CONTENT) },
            failure = { throw FridgePalException(HttpStatus.valueOf(it.errorCode), it.errorMessage) },
        )
        return response
    }
}
