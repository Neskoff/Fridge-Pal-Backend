package com.simkord.fridgepalbackend.application.request

import jakarta.validation.constraints.AssertTrue
import org.springframework.format.annotation.DateTimeFormat
import java.time.OffsetDateTime

data class ProductFilters(
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val storedBefore: OffsetDateTime? = null,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val storedAfter: OffsetDateTime? = null,

    val expired: Boolean? = false,
) {
    @get:AssertTrue(message = "storedAfter must be earlier than storedBefore")
    @Suppress("unused")
    val isDateOrderValid: Boolean
        get() = storedBefore == null || storedAfter == null || storedAfter.isBefore(storedBefore)
}
