package com.simkord.fridgepalbackend.datasource.database

import com.github.michaelbull.result.*
import com.simkord.fridgepalbackend.datasource.ProductDataSource
import com.simkord.fridgepalbackend.datasource.database.entity.ProductEntity
import com.simkord.fridgepalbackend.datasource.database.jpa.ProductJpaRepository
import com.simkord.fridgepalbackend.datasource.database.mapper.toDatasourceResult
import com.simkord.fridgepalbackend.datasource.database.model.DatasourceError
import org.springframework.stereotype.Component
import java.util.Optional

@Component
class ProductDatabaseDataSource(
    private val productJpaRepository: ProductJpaRepository,
) : ProductDataSource {

    override fun getProducts(): Result<MutableList<ProductEntity>, DatasourceError> {
        return runCatching { productJpaRepository.findAll() }.toDatasourceResult()
    }

    override fun saveProduct(product: ProductEntity): Result<ProductEntity, DatasourceError> {
        return runCatching { productJpaRepository.save(product) }.toDatasourceResult()
    }

    override fun deleteProductById(productId: Long): Result<Unit, DatasourceError> {
        return runCatching { productJpaRepository.deleteById(productId) }.toDatasourceResult()
    }

    override fun productExistsById(productId: Long): Result<Boolean, DatasourceError> {
        return runCatching { productJpaRepository.existsById(productId) }.toDatasourceResult()
    }

    override fun getProductById(productId: Long): Result<Optional<ProductEntity>, DatasourceError> {
        return runCatching { productJpaRepository.findById(productId) }.toDatasourceResult()
    }
}
