package com.simkord.fridgepalbackend.service

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.fold
import com.simkord.fridgepalbackend.datasource.ProductDataSource
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
}
