package com.simkord.fridgepalbackend.application.response

import com.simkord.fridgepalbackend.service.enums.ProductType
import com.simkord.fridgepalbackend.service.enums.QuantityUnit
import java.time.LocalDate

data class ProductResponse(
    val name: String,
    val type: ProductType,
    val quantity: Double,
    val quantityUnit: QuantityUnit,
    val storedDate: LocalDate,
    val expiryDate: LocalDate,
)
