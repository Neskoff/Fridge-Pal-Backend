package com.simkord.fridgepalbackend.application.request

import com.simkord.fridgepalbackend.service.enums.ProductType
import com.simkord.fridgepalbackend.service.enums.QuantityUnit
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.PastOrPresent
import org.springframework.format.annotation.DateTimeFormat
import java.time.OffsetDateTime

data class ProductRequest(
    val name: String,
    val type: ProductType,
    val quantity: Double,
    val quantityUnit: QuantityUnit,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @field:PastOrPresent(message = "storedDate must be in the past or present")
    val storedDate: OffsetDateTime,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @field:Future(message = "expiryDate must be in the future")
    val expiryDate: OffsetDateTime,
)
