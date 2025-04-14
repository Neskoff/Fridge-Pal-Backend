package com.simkord.fridgepalbackend.application.mapper

import com.simkord.fridgepalbackend.application.request.ProductRequest
import com.simkord.fridgepalbackend.application.response.ProductResponse
import com.simkord.fridgepalbackend.service.model.Product

fun List<Product>.toProductResponseList(): MutableList<ProductResponse> {
    return this.map { it.toProductResponse() }.toMutableList()
}

fun Product.toProductResponse(): ProductResponse {
    return ProductResponse(
        id = id,
        name = name,
        type = type.label,
        quantity = quantity,
        quantityUnit = quantityUnit.label,
        storedDate = storedDate,
        expiryDate = expiryDate,
    )
}

fun ProductRequest.toProduct(): Product {
    return Product(
        id = 0,
        name = name,
        type = type,
        quantity = quantity,
        quantityUnit = quantityUnit,
        storedDate = storedDate,
        expiryDate = expiryDate,
    )
}
