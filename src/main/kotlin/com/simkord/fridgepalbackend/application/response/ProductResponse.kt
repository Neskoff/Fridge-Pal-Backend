package com.simkord.fridgepalbackend.application.response

import java.time.LocalDate

data class ProductResponse(
    val name: String,
    val type: String,
    val quantity: Double,
    val quantityUnit: String,
    val storedDate: LocalDate,
    val expiryDate: LocalDate,
)
