package com.simkord.fridgepalbackend.datasource.database.jpa

import com.simkord.fridgepalbackend.datasource.database.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface ProductJpaRepository : JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity>
