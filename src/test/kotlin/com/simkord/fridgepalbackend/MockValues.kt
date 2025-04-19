package com.simkord.fridgepalbackend

import com.simkord.fridgepalbackend.application.request.ProductRequest
import com.simkord.fridgepalbackend.datasource.database.entity.ProductEntity
import com.simkord.fridgepalbackend.datasource.database.entity.ProductTypeEntity
import com.simkord.fridgepalbackend.datasource.database.entity.QuantityUnitEntity
import com.simkord.fridgepalbackend.datasource.database.enums.DatabaseProductType
import com.simkord.fridgepalbackend.datasource.database.enums.DatabaseQuantityUnit
import com.simkord.fridgepalbackend.datasource.database.model.DatasourceError
import com.simkord.fridgepalbackend.service.enums.ProductType
import com.simkord.fridgepalbackend.service.enums.QuantityUnit
import com.simkord.fridgepalbackend.service.mapper.toProduct
import com.simkord.fridgepalbackend.service.model.Product
import com.simkord.fridgepalbackend.service.model.ProductImage
import com.simkord.fridgepalbackend.service.model.ServiceError
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import java.time.LocalDate

private const val PRODUCT_ID = 1L
private const val PRODUCT_NAME = "Apples"
private const val GENERIC_ERROR = "Generic error"

fun mockProductName(): String {
    return PRODUCT_NAME
}

fun mockProductId(): Long {
    return PRODUCT_ID
}

fun mockGenericErrorDescription(): String {
    return GENERIC_ERROR
}

val productImage = ProductImage(
    imageId = "mockId",
    imageUrl = "mockImageUrl",
)

val productEntity = ProductEntity().apply {
    id = PRODUCT_ID
    name = PRODUCT_NAME
    productType = ProductTypeEntity().apply {
        id = PRODUCT_ID
        name = DatabaseProductType.FRUITS
    }
    quantity = 1.0
    quantityUnit = QuantityUnitEntity().apply {
        id = PRODUCT_ID
        name = DatabaseQuantityUnit.KILOGRAM
    }
    storedDate = LocalDate.now()
    expiryDate = LocalDate.now().plusDays(1)
}

val product = productEntity.toProduct()

val productRequest = ProductRequest(
    name = PRODUCT_NAME,
    type = ProductType.FRUITS,
    quantity = 1.0,
    quantityUnit = QuantityUnit.KILOGRAM,
    storedDate = LocalDate.now(),
    expiryDate = LocalDate.now().plusDays(1),
)

val productList = mutableListOf(product)

val productEntityList = mutableListOf(productEntity)

val expiredProductList = mutableListOf(product.copy(expiryDate = LocalDate.now(), expired = true))

fun mockProductList(): MutableList<Product> {
    return productList
}

fun mockProductEntityList(): MutableList<ProductEntity> {
    return productEntityList
}

fun mockExpiredProductList(): MutableList<Product> {
    return expiredProductList
}

fun mockProductImage(): ProductImage {
    return productImage
}

fun mockProduct(): Product {
    return product
}

fun mockProductEntity(): ProductEntity {
    return productEntity
}

fun mockProductRequest(): ProductRequest {
    return productRequest
}

fun mockServiceError500(): ServiceError {
    return ServiceError(HttpStatus.INTERNAL_SERVER_ERROR.value(), GENERIC_ERROR)
}

fun mockServiceError404(): ServiceError {
    return ServiceError(HttpStatus.NOT_FOUND.value(), GENERIC_ERROR)
}

fun mockDatasourceError500(): DatasourceError {
    return DatasourceError(HttpStatus.INTERNAL_SERVER_ERROR.value(), GENERIC_ERROR)
}

fun mockDatasourceError404(): DatasourceError {
    return DatasourceError(HttpStatus.NOT_FOUND.value(), GENERIC_ERROR)
}

fun mockFile(): MockMultipartFile {
    return MockMultipartFile(
        "file",
        "image.jpg",
        MediaType.IMAGE_JPEG_VALUE,
        "dummy image content".toByteArray(),
    )
}
