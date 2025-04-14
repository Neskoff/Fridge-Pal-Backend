package com.simkord.fridgepalbackend.datasource.database.entity

import jakarta.persistence.*

@Entity
@Table(name = "app_users")
 class AppUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    var username: String = ""

    var password: String = ""

    var role: String = ""
}