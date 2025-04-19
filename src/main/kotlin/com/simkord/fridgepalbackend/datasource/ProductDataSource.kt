package com.simkord.fridgepalbackend.datasource

import com.github.michaelbull.result.Result
import com.simkord.fridgepalbackend.application.request.ProductFilters
import com.simkord.fridgepalbackend.datasource.database.entity.ProductEntity
import com.simkord.fridgepalbackend.datasource.database.model.DatasourceError
import java.util.Optional

interface ProductDataSource {

    fun getProducts(filters: ProductFilters): Result<MutableList<ProductEntity>, DatasourceError>

    fun saveProduct(product: ProductEntity): Result<ProductEntity, DatasourceError>

    fun deleteProductById(productId: Long): Result<Unit, DatasourceError>

    fun productExistsById(productId: Long): Result<Boolean, DatasourceError>

    fun getProductById(productId: Long): Result<Optional<ProductEntity>, DatasourceError>
}
