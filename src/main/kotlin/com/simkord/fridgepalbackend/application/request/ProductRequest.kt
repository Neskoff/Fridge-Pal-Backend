package com.simkord.fridgepalbackend.application.request

import com.simkord.fridgepalbackend.service.enums.ProductType
import com.simkord.fridgepalbackend.service.enums.QuantityUnit
import java.time.LocalDate

data class ProductRequest(
    val name: String,
    val type: ProductType,
    val quantity: Double,
    val quantityUnit: QuantityUnit,
    val storedDate: LocalDate,
    val expiryDate: LocalDate,
)
