package com.simkord.fridgepalbackend.application.request

import com.simkord.fridgepalbackend.service.enums.ProductType
import com.simkord.fridgepalbackend.service.enums.QuantityUnit
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.PastOrPresent
import java.time.LocalDate

data class ProductRequest(
    val name: String,
    val type: ProductType,
    val quantity: Double,
    val quantityUnit: QuantityUnit,
    @field:PastOrPresent(message = "storedDate must be in the past or present")
    val storedDate: LocalDate,
    @field:Future(message = "expiryDate must be in the future")
    val expiryDate: LocalDate,
)
