package com.simkord.fridgepalbackend.datasource

import com.simkord.fridgepalbackend.datasource.database.entity.ProductEntity

interface ProductDataSource {

    fun getProducts(): MutableList<ProductEntity>
}
