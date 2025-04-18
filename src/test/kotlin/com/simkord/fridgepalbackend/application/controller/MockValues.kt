package com.simkord.fridgepalbackend.application.controller

import com.simkord.fridgepalbackend.application.request.ProductRequest
import com.simkord.fridgepalbackend.service.enums.ProductType
import com.simkord.fridgepalbackend.service.enums.QuantityUnit
import com.simkord.fridgepalbackend.service.model.Product
import com.simkord.fridgepalbackend.service.model.ServiceError
import org.springframework.http.HttpStatus
import java.time.LocalDate

private const val PRODUCT_ID = 1L
private const val PRODUCT_NAME = "Apples"
private const val SERVICE_ERROR = "Service error"
private const val IMAGE_ID = "1"

fun mockProductName(): String {
    return PRODUCT_NAME
}

fun mockProductId(): Long {
    return PRODUCT_ID
}

fun mockServiceErrorDescription(): String {
    return SERVICE_ERROR
}

fun mockImageId(): String {
    return IMAGE_ID
}

val product = Product(
    id = PRODUCT_ID,
    name = PRODUCT_NAME,
    type = ProductType.FRUITS,
    quantity = 1.0,
    quantityUnit = QuantityUnit.KILOGRAM,
    storedDate = LocalDate.now(),
    expiryDate = LocalDate.now().plusDays(1),
)

val productRequest = ProductRequest(
    name = PRODUCT_NAME,
    type = ProductType.FRUITS,
    quantity = 1.0,
    quantityUnit = QuantityUnit.KILOGRAM,
    storedDate = LocalDate.now(),
    expiryDate = LocalDate.now().plusDays(1),
)

val productList = mutableListOf(product)

fun mockProductList(): MutableList<Product> {
    return productList
}

fun mockProduct(): Product {
    return product
}

fun mockProductRequest(): ProductRequest {
    return productRequest
}

fun mockServiceError500(): ServiceError {
    return ServiceError(HttpStatus.INTERNAL_SERVER_ERROR.value(), SERVICE_ERROR)
}

fun mockServiceError404(): ServiceError {
    return ServiceError(HttpStatus.NOT_FOUND.value(), SERVICE_ERROR)
}
