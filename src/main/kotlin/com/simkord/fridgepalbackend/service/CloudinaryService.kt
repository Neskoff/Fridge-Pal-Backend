package com.simkord.fridgepalbackend.service

import com.cloudinary.Cloudinary
import com.github.michaelbull.result.*
import com.simkord.fridgepalbackend.service.model.ServiceError
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class CloudinaryService(
    private val cloudinary: Cloudinary,

) {
    fun uploadImageToCloudinary(image: MultipartFile): Result<String, ServiceError> {
        val uploadedFile = runCatching {
            cloudinary.uploader().upload(image.bytes, HashMap<Any, Any>())
        }.fold(
            success = { it },
            failure = { return@uploadImageToCloudinary Err(ServiceError(HttpStatus.INTERNAL_SERVER_ERROR.value(), CLOUDINARY_UPLOAD_ERROR)) },
        )

        val publicId = uploadedFile[PUBLIC_ID].toString()
        return Ok(cloudinary.url().secure(true).generate(publicId))
    }

    companion object {
        private const val PUBLIC_ID = "public_id"
        private const val CLOUDINARY_UPLOAD_ERROR = "Error while uploading image"
    }
}
