package com.simkord.fridgepalbackend.application.request

import jakarta.validation.constraints.AssertTrue
import java.time.LocalDate

data class ProductFilters(
    val storedBefore: LocalDate? = null,
    val storedAfter: LocalDate? = null,
    val expired: Boolean? = false,
) {
    @get:AssertTrue(message = "storedAfter must be earlier than storedBefore")
    @Suppress("unused")
    val isDateOrderValid: Boolean
        get() = storedBefore == null || storedAfter == null || storedAfter.isBefore(storedBefore)
}
