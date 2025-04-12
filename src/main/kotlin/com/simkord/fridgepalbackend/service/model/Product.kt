package com.simkord.fridgepalbackend.service.model

import com.simkord.fridgepalbackend.service.enums.ProductTypeResponse
import com.simkord.fridgepalbackend.service.enums.QuantityUnit
import java.time.LocalDate

data class Product(
    val name: String,
    val type: ProductTypeResponse,
    val quantity: Double,
    val quantityUnit: QuantityUnit,
    val storedDate: LocalDate,
    val expiryDate: LocalDate,
)
