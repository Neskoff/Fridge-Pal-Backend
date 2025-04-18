package com.simkord.fridgepalbackend.service.model

import com.simkord.fridgepalbackend.service.enums.ProductType
import com.simkord.fridgepalbackend.service.enums.QuantityUnit
import java.time.LocalDate

data class Product(
    var id: Long = 0,
    val name: String,
    val type: ProductType,
    val quantity: Double,
    val quantityUnit: QuantityUnit,
    val storedDate: LocalDate,
    val expiryDate: LocalDate,
    var productImage: ProductImage? = null,
)
