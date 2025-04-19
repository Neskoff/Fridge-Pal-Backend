package com.simkord.fridgepalbackend.service.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.simkord.fridgepalbackend.service.enums.UserRole
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class AppUser(
    @JsonIgnore
    val userUsername: String,
    @JsonIgnore
    val userPassword: String,
    @JsonIgnore
    val userRole: UserRole,
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
