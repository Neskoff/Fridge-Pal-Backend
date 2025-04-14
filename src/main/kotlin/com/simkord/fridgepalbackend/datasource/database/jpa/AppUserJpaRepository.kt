package com.simkord.fridgepalbackend.datasource.database.jpa

import com.simkord.fridgepalbackend.datasource.database.entity.AppUserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface AppUserJpaRepository: JpaRepository<AppUserEntity, Long> {
    fun findByUsername(username: String): Optional<AppUserEntity>
}