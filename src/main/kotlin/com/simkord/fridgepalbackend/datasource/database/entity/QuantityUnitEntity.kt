package com.simkord.fridgepalbackend.datasource.database.entity

import com.simkord.fridgepalbackend.datasource.database.enums.DatabaseQuantityUnit
import jakarta.persistence.*

@Entity
@Table(name = "quantity_types")
class QuantityUnitEntity {

    @Id
    @Column(name = "id", nullable = false)
    var id: Long = 0

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    var name: DatabaseQuantityUnit? = null
}
