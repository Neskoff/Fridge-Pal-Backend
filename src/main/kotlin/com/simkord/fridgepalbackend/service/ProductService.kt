package com.simkord.fridgepalbackend.service

import com.github.michaelbull.result.*
import com.simkord.fridgepalbackend.datasource.ProductDataSource
import com.simkord.fridgepalbackend.service.mapper.toProduct
import com.simkord.fridgepalbackend.service.mapper.toProductEntity
import com.simkord.fridgepalbackend.service.mapper.toProductList
import com.simkord.fridgepalbackend.service.mapper.toServiceError
import com.simkord.fridgepalbackend.service.model.Product
import com.simkord.fridgepalbackend.service.model.ServiceError
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ProductService(
    private val productDataSource: ProductDataSource,
    private val cloudinaryService: CloudinaryService,
) {

    fun getProducts(): Result<MutableList<Product>, ServiceError> {
        return productDataSource.getProducts().fold(
            success = { Ok(it.toProductList()) },
            failure = { Err(it.toServiceError()) },
        )
    }

    fun saveProduct(product: Product): Result<Product, ServiceError> {
        return productDataSource.saveProduct(product.toProductEntity()).fold(
            success = { Ok(it.toProduct()) },
            failure = { Err(it.toServiceError()) },
        )
    }

    fun updateProduct(product: Product): Result<Product, ServiceError> {
        checkProductExistsById(product.id).onFailure {
            return@updateProduct Err(it)
        }

        return productDataSource.saveProduct(product.toProductEntity()).fold(
            success = { Ok(it.toProduct()) },
            failure = { Err(it.toServiceError()) },
        )
    }

    fun deleteProduct(productId: Long): Result<Unit, ServiceError> {
        checkProductExistsById(productId).onFailure {
            return@deleteProduct Err(it)
        }

        return productDataSource.deleteProductById(productId).fold(
            success = { Ok(Unit) },
            failure = { Err(it.toServiceError()) },
        )
    }

    fun updateProductImage(image: MultipartFile, productId: Long): Result<Product, ServiceError> {
        checkProductExistsById(productId).onFailure {
            return@updateProductImage Err(it)
        }

        val product = productDataSource.getProductById(productId).fold(
            success = { it.toProduct() },
            failure = { return@updateProductImage Err(it.toServiceError()) },
        )

        val imageUrl = cloudinaryService.uploadImageToCloudinary(image).fold(
            success = { it },
            failure = { return@updateProductImage Err(it) },
        )

        product.imageUrl = imageUrl

        return productDataSource.saveProduct(product.toProductEntity()).fold(
            success = { Ok(it.toProduct()) },
            failure = { Err(it.toServiceError()) },
        )
    }

    private fun checkProductExistsById(productId: Long): Result<Unit, ServiceError> {
        val productExists = productDataSource.productExistsById(productId).fold(
            success = { it },
            failure = { return@checkProductExistsById Err(it.toServiceError()) },
        )

        if (!productExists) {
            return Err(ServiceError(HttpStatus.NOT_FOUND.value(), "Product Not Found for the id $productId"))
        }

        return Ok(Unit)
    }
}
