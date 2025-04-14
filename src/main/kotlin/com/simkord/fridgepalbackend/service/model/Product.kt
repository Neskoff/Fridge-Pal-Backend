package com.simkord.fridgepalbackend.service.model

import com.simkord.fridgepalbackend.service.enums.ProductType
import com.simkord.fridgepalbackend.service.enums.QuantityUnit
import java.time.LocalDate

data class Product(
    val id: Long,
    val name: String,
    val type: ProductType,
    val quantity: Double,
    val quantityUnit: QuantityUnit,
    val storedDate: LocalDate,
    val expiryDate: LocalDate,
)
