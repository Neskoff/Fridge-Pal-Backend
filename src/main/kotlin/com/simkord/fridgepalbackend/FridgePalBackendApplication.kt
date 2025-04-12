package com.simkord.fridgepalbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FridgePalBackendApplication

fun main(args: Array<String>) {
    runApplication<FridgePalBackendApplication>(*args)
}
