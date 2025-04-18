package com.simkord.fridgepalbackend.service

import com.github.michaelbull.result.*
import com.simkord.fridgepalbackend.datasource.ProductDataSource
import com.simkord.fridgepalbackend.service.mapper.*
import com.simkord.fridgepalbackend.service.model.Product
import com.simkord.fridgepalbackend.service.model.ServiceError
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ProductService(
    private val productDataSource: ProductDataSource,
    private val cloudinaryService: CloudinaryService,
) {

    fun getProducts(): Result<MutableList<Product>, ServiceError> {
        return productDataSource.getProducts().mapToServiceResult { it.toProductList() }
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

    fun deleteProduct(productId: Long): Result<Unit, ServiceError> {
        checkProductExistsById(productId).onFailure {
            return@deleteProduct Err(it)
        }

        return productDataSource.deleteProductById(productId).mapToServiceResult { }
    }

    fun updateProductImage(image: MultipartFile, productId: Long): Result<Product, ServiceError> {
        val product = productDataSource.getProductById(productId).fold(
            success = { it },
            failure = { return@updateProductImage Err(it.toServiceError()) },
        )

        verifyProductExists(product.isPresent, productId).onFailure {
            return@updateProductImage Err(it)
        }

        val existingProduct = product.get()

        val uploadedImage = cloudinaryService.uploadImageToCloudinary(image).fold(
            success = { it },
            failure = { return@updateProductImage Err(it) },
        )

        existingProduct.imageUrl = uploadedImage.imageUrl
        existingProduct.imageId = uploadedImage.imageId

        return productDataSource.saveProduct(existingProduct).mapToServiceResult { it.toProduct() }
    }

    private fun checkProductExistsById(productId: Long): Result<Unit, ServiceError> {
        val productExists = productDataSource.productExistsById(productId).fold(
            success = { it },
            failure = { return@checkProductExistsById Err(it.toServiceError()) },
        )
        return verifyProductExists(productExists, productId)
    }

    fun deleteProductImage(imageId: String): Result<Unit, ServiceError> {
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
}
