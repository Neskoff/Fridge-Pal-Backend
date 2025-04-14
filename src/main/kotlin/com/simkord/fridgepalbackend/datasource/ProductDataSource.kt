package com.simkord.fridgepalbackend.datasource

import com.github.michaelbull.result.Result
import com.simkord.fridgepalbackend.datasource.database.entity.ProductEntity
import com.simkord.fridgepalbackend.datasource.database.model.DatasourceError

interface ProductDataSource {

    fun getProducts(): Result<MutableList<ProductEntity>, DatasourceError>

    fun saveProduct(product: ProductEntity): Result<ProductEntity, DatasourceError>

    fun deleteProductById(productId: Long): Result<Unit, DatasourceError>

    fun productExistsById(productId: Long): Result<Boolean, DatasourceError>
}
