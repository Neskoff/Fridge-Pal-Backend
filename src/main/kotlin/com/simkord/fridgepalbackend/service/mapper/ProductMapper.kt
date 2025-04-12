package com.simkord.fridgepalbackend.service.mapper

import com.simkord.fridgepalbackend.datasource.database.entity.ProductEntity
import com.simkord.fridgepalbackend.service.model.Product

fun List<ProductEntity>.toProductList(): MutableList<Product> {
    return this.map { it.toProduct() }.toMutableList()
}

fun ProductEntity.toProduct(): Product {
    return Product(
        name = name,
        type = productType?.name.toString(),
        quantity = quantity,
        quantityUnit = quantityUnit?.name.toString(),
        storedDate = storedDate,
        expiryDate = expiryDate,
    )
}
