package com.simkord.fridgepalbackend.service

import com.github.michaelbull.result.*
import com.simkord.fridgepalbackend.application.request.ProductFilters
import com.simkord.fridgepalbackend.datasource.ProductDataSource
import com.simkord.fridgepalbackend.datasource.database.entity.ProductEntity
import com.simkord.fridgepalbackend.service.mapper.*
import com.simkord.fridgepalbackend.service.model.Product
import com.simkord.fridgepalbackend.service.model.ServiceError
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class ProductService(
    private val productDataSource: ProductDataSource,
    private val cloudinaryService: CloudinaryService,
) {

    fun getProducts(filters: ProductFilters): Result<MutableList<Product>, ServiceError> {
        return productDataSource.getProducts(filters).mapToServiceResult { it.toProductList() }
    }

    fun saveProduct(product: Product): Result<Product, ServiceError> {
        return productDataSource.saveProduct(product.toProductEntity()).mapToServiceResult { it.toProduct() }
    }

    fun updateProduct(product: Product): Result<Product, ServiceError> {
        checkProductExistsById(product.id).onFailure {
            return@updateProduct Err(it)
        }

        return productDataSource.saveProduct(product.toProductEntity()).mapToServiceResult { it.toProduct() }
    }

    @Transactional
    fun deleteProduct(productId: Long): Result<Unit, ServiceError> {
        val existingProduct = getExistingProduct(productId).fold(
            success = { it },
            failure = { return@deleteProduct Err(it) },
        )

        productDataSource.deleteProductById(productId).fold(
            success = {},
            failure = { return@deleteProduct Err(it.toServiceError()) },
        )

        existingProduct.imageId?.let { imageId ->
            cloudinaryService.deleteImageFromCloudinary(imageId).fold(
                success = {},
                failure = { return@deleteProduct Err(it) },
            )
        }

        return Ok(Unit)
    }

    fun updateProductImage(image: MultipartFile, productId: Long): Result<Product, ServiceError> {
        val existingProduct = getExistingProduct(productId).fold(
            success = { it },
            failure = { return@updateProductImage Err(it) },
        )

        val uploadedImage = cloudinaryService.uploadImageToCloudinary(image).fold(
            success = { it },
            failure = { return@updateProductImage Err(it) },
        )

        existingProduct.imageUrl = uploadedImage.imageUrl
        existingProduct.imageId = uploadedImage.imageId

        val result = productDataSource.saveProduct(existingProduct).mapToServiceResult { it.toProduct() }

        if (result.isErr && uploadedImage.imageId != null) {
            cloudinaryService.deleteImageFromCloudinary(uploadedImage.imageId)
        }

        return result
    }

    private fun checkProductExistsById(productId: Long): Result<Unit, ServiceError> {
        val productExists = productDataSource.productExistsById(productId).fold(
            success = { it },
            failure = { return@checkProductExistsById Err(it.toServiceError()) },
        )
        return verifyProductExists(productExists, productId)
    }

    @Transactional
    fun deleteProductImage(productId: Long): Result<Unit, ServiceError> {
        val existingProduct = getExistingProduct(productId).fold(
            success = { it },
            failure = { return@deleteProductImage Err(it) },
        )

        if (existingProduct.imageId == null) {
            return Err(ServiceError(HttpStatus.NOT_FOUND.value(), "Existing image not found for product $productId"))
        }

        val imageId = existingProduct.imageId!!

        existingProduct.imageId = null
        existingProduct.imageUrl = null

        productDataSource.saveProduct(existingProduct).fold(
            success = { it },
            failure = { return@deleteProductImage Err(it.toServiceError()) },
        )

        return cloudinaryService.deleteImageFromCloudinary(imageId).fold(
            success = { Ok(Unit) },
            failure = { Err(it) },
        )
    }

    private fun verifyProductExists(productExists: Boolean, productId: Long): Result<Unit, ServiceError> {
        if (!productExists) {
            return Err(ServiceError(HttpStatus.NOT_FOUND.value(), "Product Not Found for the id $productId"))
        }

        return Ok(Unit)
    }

    private fun getExistingProduct(productId: Long): Result<ProductEntity, ServiceError> {
        val product = productDataSource.getProductById(productId).fold(
            success = { it },
            failure = { return@getExistingProduct Err(it.toServiceError()) },
        )

        verifyProductExists(product.isPresent, productId).onFailure {
            return@getExistingProduct Err(it)
        }

        return Ok(product.get())
    }
}
