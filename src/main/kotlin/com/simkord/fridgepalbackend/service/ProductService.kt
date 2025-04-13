package com.simkord.fridgepalbackend.service

import com.github.michaelbull.result.*
import com.simkord.fridgepalbackend.datasource.ProductDataSource
import com.simkord.fridgepalbackend.service.mapper.toProduct
import com.simkord.fridgepalbackend.service.mapper.toProductEntity
import com.simkord.fridgepalbackend.service.mapper.toProductList
import com.simkord.fridgepalbackend.service.mapper.toServiceError
import com.simkord.fridgepalbackend.service.model.Product
import com.simkord.fridgepalbackend.service.model.ServiceError
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
            failure = { return@saveProduct Err(ServiceError(400, "Invalid Product Details ${it.message}")) },
        )
        return productDataSource.saveProduct(productEntity).fold(
            success = { Ok(it.toProduct()) },
            failure = { Err(it.toServiceError()) },
        )
    }
}
