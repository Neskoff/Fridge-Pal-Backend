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

@Service
class ProductService(
    private val productDataSource: ProductDataSource,
) {

    fun getProducts(): Result<MutableList<Product>, ServiceError> {
        return productDataSource.getProducts().fold(
            success = { Ok(it.toProductList()) },
            failure = { Err(it.toServiceError()) },
        )
    }

    fun saveProduct(product: Product): Result<Product, ServiceError> {
        val productEntity = runCatching {
            product.toProductEntity()
        }.fold(
            success = { it },
            failure = { return@saveProduct Err(ServiceError(HttpStatus.BAD_REQUEST.value(), "Invalid Product Details ${it.message}")) },
        )
        return productDataSource.saveProduct(productEntity).fold(
            success = { Ok(it.toProduct()) },
            failure = { Err(it.toServiceError()) },
        )
    }

    fun deleteProduct(productId: Long): Result<Unit, ServiceError> {
        val productExists = productDataSource.productExistsById(productId).fold(
            success = { it },
            failure = { return@deleteProduct Err(it.toServiceError()) },
        )

        if (!productExists) {
            return Err(ServiceError(HttpStatus.NOT_FOUND.value(), "Product Not Found"))
        }

        return productDataSource.deleteProductById(productId).fold(
            success = { Ok(Unit) },
            failure = { Err(it.toServiceError()) },
        )
    }
}
