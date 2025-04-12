package com.simkord.fridgepalbackend.datasource.database

import com.simkord.fridgepalbackend.datasource.ProductDataSource
import com.simkord.fridgepalbackend.datasource.database.entity.ProductEntity
import com.simkord.fridgepalbackend.datasource.database.jpa.ProductJpaRepository
import org.springframework.stereotype.Component

@Component
class ProductDatabaseDataSource(
    private val productJpaRepository: ProductJpaRepository,
) : ProductDataSource {

    override fun getProducts(): MutableList<ProductEntity> {
        return productJpaRepository.findAll()
    }
}
