package com.simkord.fridgepalbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class FridgePalBackendApplication

fun main(args: Array<String>) {
    runApplication<FridgePalBackendApplication>(*args)
}
