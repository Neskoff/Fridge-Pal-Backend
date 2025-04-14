package com.simkord.fridgepalbackend.service.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class AppUser (
    val userUsername: String,
    val userPassword: String,
    val userRole: String,
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority("ROLE_$userRole"))
    }

    override fun getPassword(): String {
        return userPassword
    }

    override fun getUsername(): String {
        return userUsername
    }
}