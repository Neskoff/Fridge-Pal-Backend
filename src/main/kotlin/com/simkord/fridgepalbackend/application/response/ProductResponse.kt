package com.simkord.fridgepalbackend.application.response

import com.simkord.fridgepalbackend.service.model.ProductImage
import java.time.OffsetDateTime

data class ProductResponse(
    val id: Long,
    val name: String,
    val type: String,
    val quantity: Double,
    val quantityUnit: String,
    val storedDate: OffsetDateTime,
    val expiryDate: OffsetDateTime,
    var productImage: ProductImage? = null,
    val expired: Boolean? = null,
)
