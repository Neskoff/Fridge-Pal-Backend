package com.simkord.fridgepalbackend.datasource.database

import com.github.michaelbull.result.*
import com.simkord.fridgepalbackend.datasource.ProductDataSource
import com.simkord.fridgepalbackend.datasource.database.entity.ProductEntity
import com.simkord.fridgepalbackend.datasource.database.jpa.ProductJpaRepository
import com.simkord.fridgepalbackend.datasource.database.model.DatasourceError
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class ProductDatabaseDataSource(
    private val productJpaRepository: ProductJpaRepository,
) : ProductDataSource {

    override fun getProducts(): Result<MutableList<ProductEntity>, DatasourceError> {
        return runCatching {
            productJpaRepository.findAll()
        }.fold(
            success = { Ok(it) },
            failure = { Err(DatasourceError(HttpStatus.INTERNAL_SERVER_ERROR.value(), UNEXPECTED_DATABASE_ERROR_MESSAGE)) },
        )
    }

    override fun saveProduct(product: ProductEntity): Result<ProductEntity, DatasourceError> {
        return runCatching {
            productJpaRepository.save(product)
        }.fold(
            success = { Ok(it) },
            failure = { Err(DatasourceError(HttpStatus.INTERNAL_SERVER_ERROR.value(), UNEXPECTED_DATABASE_ERROR_MESSAGE)) },
        )
    }

    override fun deleteProductById(productId: Long): Result<Unit, DatasourceError> {
        return runCatching {
            productJpaRepository.deleteById(productId)
        }.fold(
            success = { Ok(Unit) },
            failure = { Err(DatasourceError(HttpStatus.INTERNAL_SERVER_ERROR.value(), UNEXPECTED_DATABASE_ERROR_MESSAGE)) },
        )
    }

    override fun productExistsById(productId: Long): Result<Boolean, DatasourceError> {
        return runCatching {
            productJpaRepository.existsById(productId)
        }.fold(
            success = { Ok(it) },
            failure = { Err(DatasourceError(HttpStatus.INTERNAL_SERVER_ERROR.value(), UNEXPECTED_DATABASE_ERROR_MESSAGE)) },
        )
    }

    companion object {
        private const val UNEXPECTED_DATABASE_ERROR_MESSAGE = "Unexpected database error"
    }
}
