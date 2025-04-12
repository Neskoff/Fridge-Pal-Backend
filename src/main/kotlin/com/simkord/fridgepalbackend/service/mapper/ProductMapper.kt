package com.simkord.fridgepalbackend.service.mapper

import com.simkord.fridgepalbackend.datasource.database.entity.ProductEntity
import com.simkord.fridgepalbackend.datasource.database.enums.DatabaseProductType
import com.simkord.fridgepalbackend.datasource.database.enums.DatabaseQuantityUnit
import com.simkord.fridgepalbackend.datasource.database.model.DatasourceError
import com.simkord.fridgepalbackend.service.enums.ProductTypeResponse
import com.simkord.fridgepalbackend.service.enums.QuantityUnit
import com.simkord.fridgepalbackend.service.model.Product
import com.simkord.fridgepalbackend.service.model.ServiceError

fun List<ProductEntity>.toProductList(): MutableList<Product> {
    return this.map { it.toProduct() }.toMutableList()
}

fun ProductEntity.toProduct(): Product {
    return Product(
        name = name,
        type = productType!!.name!!.toProductType(),
        quantity = quantity,
        quantityUnit = quantityUnit!!.name!!.toQuantityUnit(),
        storedDate = storedDate,
        expiryDate = expiryDate,
    )
}

fun DatasourceError.toServiceError(): ServiceError {
    return ServiceError(
        errorCode = errorCode,
        errorMessage = errorMessage,
    )
}

fun DatabaseProductType.toProductType(): ProductTypeResponse {
    return when (this) {
        DatabaseProductType.FRUITS -> ProductTypeResponse.FRUITS
        DatabaseProductType.VEGETABLES -> ProductTypeResponse.VEGETABLES
    }
}

fun DatabaseQuantityUnit.toQuantityUnit(): QuantityUnit {
     return when(this) {
        DatabaseQuantityUnit.LITER -> QuantityUnit.LITER
         DatabaseQuantityUnit.KILOGRAM -> QuantityUnit.KILOGRAM
         DatabaseQuantityUnit.PIECE -> QuantityUnit.PIECE
     }
}
