package com.simkord.fridgepalbackend.service.model

import java.time.LocalDate

data class Product(
    val name: String,
    val type: String,
    val quantity: Double,
    val quantityUnit: String,
    val storedDate: LocalDate,
    val expiryDate: LocalDate,
)
