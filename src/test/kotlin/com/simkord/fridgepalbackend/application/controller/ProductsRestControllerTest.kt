package com.simkord.fridgepalbackend.application.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.simkord.fridgepalbackend.application.mapper.toProduct
import com.simkord.fridgepalbackend.application.request.ProductFilters
import com.simkord.fridgepalbackend.service.ProductService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(
    controllers = [ProductsRestController::class],
    excludeAutoConfiguration = [SecurityAutoConfiguration::class],
)
@AutoConfigureMockMvc(addFilters = false)
class ProductsRestControllerTest : NoSecurityTestConfig() {
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var productService: ProductService

    @Test
    fun `should return 200 when products are successfully retrieved`() {
        `when`(productService.getProducts(ProductFilters())).thenReturn(Ok(mockProductList()))

        mockMvc.get(PRODUCTS_PATH)
            .andExpect { status { isOk() } }
            .andExpect { jsonPath("$.[0].name") { value(mockProductName()) } }
    }

    @Test
    fun `should return only expired products when expired filter is on`() {
        `when`(productService.getProducts(ProductFilters(expired = true))).thenReturn(Ok(mockExpiredProductList()))

        mockMvc.get("$PRODUCTS_PATH?expired=true")
            .andExpect { status { isOk() } }
            .andExpect { jsonPath("$.length()") { value(1) } }
            .andExpect { jsonPath("$.[0].name") { value(mockProductName()) } }
            .andExpect { jsonPath("$.[0].expired") { value(true) } }
    }

    @Test
    fun `should return 500 when products are not retrieved`() {
        `when`(productService.getProducts(ProductFilters())).thenReturn(Err(mockServiceError500()))

        mockMvc.get(PRODUCTS_PATH)
            .andExpect { status { isInternalServerError() } }
            .andExpect { jsonPath("$.message") { value(mockServiceErrorDescription()) } }
    }

    @Test
    fun `should return 201 when a product is saved in the database`() {
        `when`(productService.saveProduct(mockProductRequest().toProduct())).thenReturn(Ok(mockProduct()))

        mockMvc.post(PRODUCTS_PATH) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(mockProductRequest())
        }
            .andExpect { status { isCreated() } }
    }

    @Test
    fun `should return 500 when a product is not saved in the database`() {
        `when`(productService.saveProduct(mockProductRequest().toProduct())).thenReturn(Err(mockServiceError500()))

        mockMvc.post(PRODUCTS_PATH) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(mockProductRequest())
        }
            .andExpect { status { isInternalServerError() } }
            .andExpect { jsonPath("$.message") { value(mockServiceErrorDescription()) } }
    }

    @Test
    fun `should return 204 when a product is deleted in the database`() {
        `when`(productService.deleteProduct(mockProductId())).thenReturn(Ok(Unit))

        mockMvc.delete("$PRODUCTS_PATH/${mockProductId()}")
            .andExpect { status { isNoContent() } }
    }

    @Test
    fun `should return 404 when a product is not found in the database`() {
        `when`(productService.deleteProduct(mockProductId())).thenReturn(Err(mockServiceError404()))

        mockMvc.delete("$PRODUCTS_PATH/${mockProductId()}")
            .andExpect { status { isNotFound() } }
    }

    @Test
    fun `should return 500 when a product is not deleted in the database`() {
        `when`(productService.deleteProduct(mockProductId())).thenReturn(Err(mockServiceError500()))

        mockMvc.delete("$PRODUCTS_PATH/${mockProductId()}")
            .andExpect { status { isInternalServerError() } }
    }

    @Test
    fun `should return 200 when a product is updated in the database`() {
        `when`(productService.updateProduct(mockProduct())).thenReturn(Ok(mockProduct()))

        mockMvc.put("$PRODUCTS_PATH/1") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(mockProductRequest())
        }
            .andExpect { status { isOk() } }
    }

    @Test
    fun `should return 404 when a product to update is not found in the database`() {
        `when`(productService.updateProduct(mockProduct())).thenReturn(Err(mockServiceError404()))

        mockMvc.put("$PRODUCTS_PATH/${mockProductId()}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(mockProductRequest())
        }
            .andExpect { status { isNotFound() } }
    }

    @Test
    fun `should return 500 when a product is not updated in the database`() {
        `when`(productService.updateProduct(mockProduct())).thenReturn(Err(mockServiceError500()))

        mockMvc.put("$PRODUCTS_PATH/${mockProductId()}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(mockProductRequest())
        }
            .andExpect { status { isInternalServerError() } }
            .andExpect { jsonPath("$.message") { value(mockServiceErrorDescription()) } }
    }

    @Test
    fun `should return 200 when an image is uploaded`() {
        val file = mockFile()
        `when`(productService.updateProductImage(file, productId = mockProductId())).thenReturn(Ok(mockProduct()))

        mockMvc.perform(
            multipart("$PRODUCTS_PATH/${mockProductId()}/image")
                .file(file)
                .with {
                    it.method = HttpMethod.PUT.name()
                    it
                },
        )
            .andExpect(status().isOk)
    }

    @Test
    fun `should return 500 when an image is not uploaded`() {
        val file = mockFile()
        `when`(productService.updateProductImage(file, productId = mockProductId())).thenReturn(Err(mockServiceError500()))

        mockMvc.perform(
            multipart("$PRODUCTS_PATH/${mockProductId()}/image")
                .file(file)
                .with {
                    it.method = HttpMethod.PUT.name()
                    it
                },
        )
            .andExpect(status().isInternalServerError)
    }

    @Test
    fun `should return 204 when an image is deleted`() {
        `when`(productService.deleteProductImage(mockImageId())).thenReturn(Ok(Unit))

        mockMvc.delete("$PRODUCTS_PATH/image/${mockProductId()}")
            .andExpect { status { isNoContent() } }
    }

    companion object {
        private const val PRODUCTS_PATH = "/api/v1/products"
    }
}
