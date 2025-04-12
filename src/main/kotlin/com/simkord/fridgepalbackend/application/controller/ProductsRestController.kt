package com.simkord.fridgepalbackend.application.controller

import com.simkord.fridgepalbackend.application.response.ProductResponse
import com.simkord.fridgepalbackend.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/products")
class ProductsRestController(
    private val productService: ProductService,
) : ProductsController {

    @GetMapping
    override fun getProducts(): ResponseEntity<List<ProductResponse>> {
        return ResponseEntity(productService.getProducts(), HttpStatus.OK)
    }
}
