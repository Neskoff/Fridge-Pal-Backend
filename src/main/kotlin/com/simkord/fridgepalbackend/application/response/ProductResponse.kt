package com.simkord.fridgepalbackend.application.response

import com.simkord.fridgepalbackend.service.model.ProductImage
import java.time.LocalDate

data class ProductResponse(
    val id: Long,
    val name: String,
    val type: String,
    val quantity: Double,
    val quantityUnit: String,
    val storedDate: LocalDate,
    val expiryDate: LocalDate,
    var productImage: ProductImage? = null,
)
