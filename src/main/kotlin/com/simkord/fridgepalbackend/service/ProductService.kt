package com.simkord.fridgepalbackend.service

import com.simkord.fridgepalbackend.application.response.ProductResponse
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ProductService {

    fun getProducts(): List<ProductResponse> {
        return listOf(
            ProductResponse(
                name = "Bananas",
                type = "Fruit",
                quantity = 1.5,
                quantityUnit = "kilograms",
                storedDate = LocalDate.now(),
                expiryDate = LocalDate.now().plusDays(7),
            ),
        )
    }
}
