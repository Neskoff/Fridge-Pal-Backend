package com.simkord.fridgepalbackend.service

import com.cloudinary.Cloudinary
import com.github.michaelbull.result.*
import com.simkord.fridgepalbackend.service.model.ProductImage
import com.simkord.fridgepalbackend.service.model.ServiceError
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

/**
 * Cloudinary service - for manipulating images
 *
 * @property cloudinary [Cloudinary]
 */
// NOTE: The methods in this service could also be done async depending on how blocking they are
@Service
class CloudinaryService(
    private val cloudinary: Cloudinary,

) {
    /**
     * Upload image to Cloudinary
     *
     * @param image [MultipartFile]
     * @return Result<[ProductImage], [ServiceError]>
     */
    fun uploadImageToCloudinary(image: MultipartFile): Result<ProductImage, ServiceError> {
        val uploadedFile = runCatching {
            cloudinary.uploader().upload(image.bytes, HashMap<Any, Any>())
        }.fold(
            success = { it },
            failure = { return@uploadImageToCloudinary Err(ServiceError(HttpStatus.INTERNAL_SERVER_ERROR.value(), CLOUDINARY_UPLOAD_ERROR)) },
        )

        val publicId = uploadedFile[PUBLIC_ID].toString()
        val url = cloudinary.url().secure(true).generate(publicId)

        return Ok(
            ProductImage(
                imageId = publicId,
                imageUrl = url,
            ),
        )
    }

    /**
     * Delete image from Cloudinary by Cloudinary image ID
     *
     * @param imageId [String]
     * @return Result<[Unit], [ServiceError]>
     */
    fun deleteImageFromCloudinary(imageId: String): Result<Unit, ServiceError> {
        return runCatching {
            cloudinary.uploader().destroy(imageId, HashMap<Any, Any>())
        }.fold(
            success = { Ok(Unit) },
            failure = { Err(ServiceError(HttpStatus.INTERNAL_SERVER_ERROR.value(), CLOUDINARY_DELETE_ERROR)) },
        )
    }

    companion object {
        private const val PUBLIC_ID = "public_id"
        private const val CLOUDINARY_UPLOAD_ERROR = "Error while uploading image"
        private const val CLOUDINARY_DELETE_ERROR = "Error while deleting image"
    }
}
