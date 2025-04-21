package com.simkord.fridgepalbackend.service

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.simkord.fridgepalbackend.*
import com.simkord.fridgepalbackend.application.request.ProductFilters
import com.simkord.fridgepalbackend.datasource.ProductDataSource
import com.simkord.fridgepalbackend.service.mapper.toProduct
import com.simkord.fridgepalbackend.service.mapper.toProductList
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

@ExtendWith(MockitoExtension::class)
class ProductServiceTest {
    private lateinit var productService: ProductService

    @Mock
    private lateinit var cloudinaryService: CloudinaryService

    @Mock
    private lateinit var productDataSource: ProductDataSource

    @BeforeEach
    fun setUp() {
        this.productService = ProductService(productDataSource, cloudinaryService)
    }

    @Test
    fun `should return product list when fetched from database`() {
        whenever(productDataSource.getProducts(any())).thenReturn(Ok(mockProductEntityList()))

        val result = productService.getProducts(ProductFilters())

        verify(productDataSource).getProducts(ProductFilters())
        assertEquals(result, Ok(mockProductEntityList().toProductList()))
    }

    @Test
    fun `should return an error when list is not fetched from database`() {
        whenever(productDataSource.getProducts(any())).thenReturn(Err(mockDatasourceError500()))

        val result = productService.getProducts(ProductFilters())

        verify(productDataSource).getProducts(ProductFilters())
        assertEquals(result, Err(mockServiceError500()))
    }

    @Test
    fun `should return saved entity if the product is saved in the database`() {
        whenever(productDataSource.saveProduct(any())).thenReturn(Ok(mockProductEntity()))

        val result = productService.saveProduct(mockProduct())

        verify(productDataSource).saveProduct(any())
        assertEquals(result, Ok(mockProduct()))
    }

    @Test
    fun `should return an error if the product is not saved in the database`() {
        whenever(productDataSource.saveProduct(any())).thenReturn(Err(mockDatasourceError500()))

        val result = productService.saveProduct(mockProduct())

        verify(productDataSource).saveProduct(any())
        assertEquals(result, Err(mockServiceError500()))
    }

    @Test
    fun `should return product when a product is updated in the database`() {
        whenever(productDataSource.saveProduct(any())).thenReturn(Ok(mockProductEntity()))
        whenever(productDataSource.productExistsById(mockProductId())).thenReturn(Ok(true))

        val result = productService.updateProduct(mockProduct())

        verify(productDataSource).saveProduct(any())
        assertEquals(result, Ok(mockProductEntity().toProduct()))
    }

    @Test
    fun `should return an error when a product to update is not found in the database`() {
        whenever(productDataSource.productExistsById(mockProductId())).thenReturn(Err(mockDatasourceError404()))

        val result = productService.updateProduct(mockProduct())

        verify(productDataSource).productExistsById(mockProductId())
        assertEquals(result, Err(mockServiceError404()))
    }

    @Test
    fun `should return an error when a product is not updated in the database`() {
        whenever(productDataSource.saveProduct(any())).thenReturn(Err(mockDatasourceError500()))
        whenever(productDataSource.productExistsById(mockProductId())).thenReturn(Ok(true))

        val result = productService.updateProduct(mockProduct())

        verify(productDataSource).saveProduct(any())
        assertEquals(result, Err(mockServiceError500()))
    }

    @Test
    fun `should return ok when a product is deleted in the database`() {
        whenever(productDataSource.getProductById(mockProductId())).thenReturn(Ok(Optional.of(mockProductEntity())))
        whenever(productDataSource.deleteProductById(mockProductId())).thenReturn(Ok(Unit))

        val result = productService.deleteProduct(mockProductId())

        verify(productDataSource).deleteProductById(mockProductId())
        assertEquals(result, Ok(Unit))
    }

    @Test
    fun `should return an error when a product to delete is not found in the database`() {
        whenever(productDataSource.getProductById(mockProductId())).thenReturn(Ok(Optional.empty()))

        val result = productService.deleteProduct(mockProductId())

        verify(productDataSource).getProductById(mockProductId())
        assertEquals(result.error.errorCode, mockServiceError404().errorCode)
    }

    @Test
    fun `should return an error when a product is not deleted in the database`() {
        whenever(productDataSource.getProductById(mockProductId())).thenReturn(Ok(Optional.of(mockProductEntity())))
        whenever(productDataSource.deleteProductById(mockProductId())).thenReturn(Err(mockDatasourceError500()))

        val result = productService.deleteProduct(mockProductId())

        verify(productDataSource).deleteProductById(mockProductId())
        assertEquals(result, Err(mockServiceError500()))
    }

    @Test
    fun `should return ok when an image is uploaded`() {
        val file = mockFile()
        val mockProductImage = mockProductImage()
        val mockedProductEntity = mockProductEntity().apply {
            imageId = mockProductImage.imageId
            imageUrl = mockProductImage.imageUrl
        }

        whenever(productDataSource.getProductById(mockProductId())).thenReturn(Ok(Optional.of(mockProductEntity())))
        whenever(productDataSource.saveProduct(any())).thenReturn(Ok(mockedProductEntity))
        whenever(cloudinaryService.uploadImageToCloudinary(any())).thenReturn(Ok(mockProductImage))

        val result = productService.updateProductImage(file, mockProductId())

        verify(cloudinaryService).uploadImageToCloudinary(file)
        verify(productDataSource).saveProduct(any())
        verify(productDataSource).getProductById(mockProductId())
        assertEquals(result, Ok(mockedProductEntity.toProduct()))
    }

    @Test
    fun `should return an error when a product is not found in the database`() {
        val file = mockFile()
        whenever(productDataSource.getProductById(mockProductId())).thenReturn(Ok(Optional.empty()))

        val result = productService.updateProductImage(file, mockProductId())

        verify(productDataSource).getProductById(mockProductId())
        assertEquals(result.error.errorCode, mockServiceError404().errorCode)
    }

    @Test
    fun `should return an error when an image is not uploaded`() {
        val file = mockFile()
        whenever(productDataSource.getProductById(mockProductId())).thenReturn(Ok(Optional.of(mockProductEntity())))
        whenever(cloudinaryService.uploadImageToCloudinary(any())).thenReturn(Err(mockServiceError500()))

        val result = productService.updateProductImage(file, mockProductId())

        verify(cloudinaryService).uploadImageToCloudinary(file)
        verify(productDataSource).getProductById(mockProductId())
        assertEquals(result, Err(mockServiceError500()))
    }

    @Test
    fun `should return an error when an image is not saved in the database`() {
        val file = mockFile()
        val mockProductImage = mockProductImage()

        whenever(productDataSource.getProductById(mockProductId())).thenReturn(Ok(Optional.of(mockProductEntity())))
        whenever(productDataSource.saveProduct(any())).thenReturn(Err(mockDatasourceError500()))
        whenever(cloudinaryService.uploadImageToCloudinary(any())).thenReturn(Ok(mockProductImage))

        val result = productService.updateProductImage(file, mockProductId())

        verify(cloudinaryService).uploadImageToCloudinary(file)
        verify(productDataSource).saveProduct(any())
        verify(productDataSource).getProductById(mockProductId())
        assertEquals(result, Err(mockServiceError500()))
    }

    @Test
    fun `should return ok when a image is deleted`() {
        whenever(productDataSource.getProductById(mockProductId())).thenReturn(Ok(Optional.of(mockProductEntity())))
        whenever(productDataSource.saveProduct(any())).thenReturn(Ok(mockProductEntity()))
        whenever(cloudinaryService.deleteImageFromCloudinary(any())).thenReturn(Ok(Unit))

        val result = productService.deleteProductImage(mockProductId())

        verify(productDataSource).getProductById(mockProductId())
        verify(cloudinaryService).deleteImageFromCloudinary(any())
        verify(productDataSource).saveProduct(any())
        assertEquals(result, Ok(Unit))
    }
}
