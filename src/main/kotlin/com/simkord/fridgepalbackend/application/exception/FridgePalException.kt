package com.simkord.fridgepalbackend.application.exception

import org.springframework.http.HttpStatus

open class FridgePalException(
    val errorCode: HttpStatus,
    override var message: String? = "Unknown exception has occurred",
) : RuntimeException(message)
