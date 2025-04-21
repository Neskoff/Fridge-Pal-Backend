package com.simkord.fridgepalbackend.datasource.database.entity

import com.simkord.fridgepalbackend.datasource.database.enums.DatabaseProductType
import jakarta.persistence.*

@Entity
@Table(name = "product_types")
class ProductTypeEntity {

    @Id
    @Column(name = "id", nullable = false)
    var id: Long = 0

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    var name: DatabaseProductType? = null
}
