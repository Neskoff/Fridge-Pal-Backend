package com.simkord.fridgepalbackend.application.controller

import com.simkord.fridgepalbackend.application.mapper.toMappedResponseEntity
import com.simkord.fridgepalbackend.application.mapper.toProduct
import com.simkord.fridgepalbackend.application.mapper.toProductResponse
import com.simkord.fridgepalbackend.application.mapper.toProductResponseList
import com.simkord.fridgepalbackend.application.request.ProductFilters
import com.simkord.fridgepalbackend.application.request.ProductRequest
import com.simkord.fridgepalbackend.application.response.ProductResponse
import com.simkord.fridgepalbackend.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("api/v1/products")
class ProductsRestController(
    private val productService: ProductService,
) : ProductsController {

    @GetMapping
    override fun getProducts(filters: ProductFilters): ResponseEntity<MutableList<ProductResponse>> {
        return productService.getProducts(filters).toMappedResponseEntity(
            transform = { it.toProductResponseList() },
            successStatus = HttpStatus.OK,
        )
    }

    @PostMapping
    override fun saveProduct(productRequest: ProductRequest): ResponseEntity<ProductResponse> {
        return productService.saveProduct(productRequest.toProduct()).toMappedResponseEntity(
            transform = { it.toProductResponse() },
            successStatus = HttpStatus.CREATED,
        )
    }

    @DeleteMapping("/{productId}")
    override fun deleteProduct(@PathVariable productId: Long): ResponseEntity<Unit> {
        return productService.deleteProduct(productId).toMappedResponseEntity(
            transform = {},
            successStatus = HttpStatus.NO_CONTENT,
        )
    }

    @PutMapping("/{productId}")
    override fun updateProduct(productRequest: ProductRequest, @PathVariable productId: Long): ResponseEntity<ProductResponse> {
        val product = productRequest.toProduct()
        product.id = productId

        return productService.updateProduct(product).toMappedResponseEntity(
            transform = { it.toProductResponse() },
            successStatus = HttpStatus.OK,
        )
    }

    @PutMapping("/{productId}/image", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    override fun updateProductImage(@RequestParam("file") file: MultipartFile, @PathVariable productId: Long): ResponseEntity<ProductResponse> {
        return productService.updateProductImage(file, productId).toMappedResponseEntity(
            transform = { it.toProductResponse() },
            successStatus = HttpStatus.OK,
        )
    }

    @DeleteMapping("{productId}/image")
    override fun deleteProductImage(@PathVariable productId: Long): ResponseEntity<Unit> {
        return productService.deleteProductImage(productId).toMappedResponseEntity(
            transform = {},
            successStatus = HttpStatus.NO_CONTENT,
        )
    }
}
