package com.simkord.fridgepalbackend.application.request

import java.time.LocalDate

data class ProductFilters(
    val storedBefore: LocalDate? = null,
    val storedAfter: LocalDate? = null,
    val expired: Boolean? = false,
)
