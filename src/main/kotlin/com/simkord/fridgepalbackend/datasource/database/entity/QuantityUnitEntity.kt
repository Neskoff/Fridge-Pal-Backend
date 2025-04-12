package com.simkord.fridgepalbackend.datasource.database.entity

import com.simkord.fridgepalbackend.datasource.database.enums.DatabaseQuantityUnit
import jakarta.persistence.*

@Entity
@Table(name = "quantity_types")
class QuantityUnitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quantityTypeIdGenerator")
    @SequenceGenerator(
        name = "quantityTypeIdGenerator",
        sequenceName = "quantity_type_id_sequence",
        allocationSize = 1,
    )
    @Column(name = "id", nullable = false)
    var id: Long = 0

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    var name: DatabaseQuantityUnit? = null
}
