package com.simkord.fridgepalbackend.application.configuration

import com.cloudinary.Cloudinary
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CloudinaryConfig(
    @Value("\${cloudinary.url}") private val cloudinaryUrl: String,
) {
    @Bean
    fun cloudinaryClient(): Cloudinary {
        return Cloudinary(cloudinaryUrl)
    }
}
