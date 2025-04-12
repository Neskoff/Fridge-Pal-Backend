package com.simkord.fridgepalbackend.service

import com.simkord.fridgepalbackend.datasource.ProductDataSource
import com.simkord.fridgepalbackend.service.mapper.toProductList
import com.simkord.fridgepalbackend.service.model.Product
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productDataSource: ProductDataSource,
) {

    fun getProducts(): MutableList<Product> {
        return productDataSource.getProducts().toProductList()
    }
}
