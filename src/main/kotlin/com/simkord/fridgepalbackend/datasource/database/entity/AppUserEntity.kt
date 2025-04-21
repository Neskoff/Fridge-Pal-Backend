package com.simkord.fridgepalbackend.datasource.database.entity

import com.simkord.fridgepalbackend.service.enums.UserRole
import jakarta.persistence.*

@Entity
@Table(name = "app_users")
class AppUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name = "username", nullable = false, unique = true)
    var username: String = ""

    @Column(name = "password", nullable = false)
    var password: String = ""

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    var role: UserRole = UserRole.USER
}
