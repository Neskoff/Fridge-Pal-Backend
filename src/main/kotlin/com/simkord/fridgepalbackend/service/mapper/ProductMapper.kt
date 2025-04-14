package com.simkord.fridgepalbackend.service.mapper

import com.simkord.fridgepalbackend.datasource.database.entity.ProductEntity
import com.simkord.fridgepalbackend.datasource.database.entity.ProductTypeEntity
import com.simkord.fridgepalbackend.datasource.database.entity.QuantityUnitEntity
import com.simkord.fridgepalbackend.datasource.database.enums.DatabaseProductType
import com.simkord.fridgepalbackend.datasource.database.enums.DatabaseQuantityUnit
import com.simkord.fridgepalbackend.datasource.database.model.DatasourceError
import com.simkord.fridgepalbackend.service.enums.ProductType
import com.simkord.fridgepalbackend.service.enums.QuantityUnit
import com.simkord.fridgepalbackend.service.model.Product
import com.simkord.fridgepalbackend.service.model.ServiceError

fun List<ProductEntity>.toProductList(): MutableList<Product> {
    return this.map { it.toProduct() }.toMutableList()
}

fun ProductEntity.toProduct(): Product {
    return Product(
        id = id,
        name = name,
        type = productType!!.name!!.toProductType(),
        quantity = quantity,
        quantityUnit = quantityUnit!!.name!!.toQuantityUnit(),
        storedDate = storedDate,
        expiryDate = expiryDate,
    )
}

fun Product.toProductEntity(): ProductEntity {
    val databaseProduct = ProductEntity()
    val databaseProductType = ProductTypeEntity()
    val databaseQuantityUnit = QuantityUnitEntity()
    databaseProduct.id = id
    databaseProductType.id = type.id
    databaseProductType.name = type.toDatabaseProductType()
    databaseQuantityUnit.id = quantityUnit.id
    databaseQuantityUnit.name = quantityUnit.toDatabaseQuantityUnit()
    databaseProduct.name = name
    databaseProduct.quantity = quantity
    databaseProduct.productType = databaseProductType
    databaseProduct.quantityUnit = databaseQuantityUnit
    databaseProduct.storedDate = storedDate
    databaseProduct.expiryDate = expiryDate

    return databaseProduct
}

fun DatasourceError.toServiceError(): ServiceError {
    return ServiceError(
        errorCode = errorCode,
        errorMessage = errorMessage,
    )
}

fun DatabaseProductType.toProductType(): ProductType {
    return when (this) {
        DatabaseProductType.FRUITS -> ProductType.FRUITS
        DatabaseProductType.VEGETABLES -> ProductType.VEGETABLES
    }
}

fun DatabaseQuantityUnit.toQuantityUnit(): QuantityUnit {
    return when (this) {
        DatabaseQuantityUnit.LITER -> QuantityUnit.LITER
        DatabaseQuantityUnit.KILOGRAM -> QuantityUnit.KILOGRAM
        DatabaseQuantityUnit.PIECE -> QuantityUnit.PIECE
    }
}

fun ProductType.toDatabaseProductType(): DatabaseProductType {
    return when (this) {
        ProductType.FRUITS -> DatabaseProductType.FRUITS
        ProductType.VEGETABLES -> DatabaseProductType.VEGETABLES
    }
}

fun QuantityUnit.toDatabaseQuantityUnit(): DatabaseQuantityUnit {
    return when (this) {
        QuantityUnit.LITER -> DatabaseQuantityUnit.LITER
        QuantityUnit.KILOGRAM -> DatabaseQuantityUnit.KILOGRAM
        QuantityUnit.PIECE -> DatabaseQuantityUnit.PIECE
    }
}
