package com.simkord.fridgepalbackend.datasource.database.entity

import com.simkord.fridgepalbackend.datasource.database.enums.ProductTypeEnum
import jakarta.persistence.*

@Entity
@Table(name = "products")
class ProductTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productTypeIdGenerator")
    @SequenceGenerator(
        name = "productTypeIdGenerator",
        sequenceName = "product_type_id_sequence",
        allocationSize = 1,
    )
    @Column(name = "id", nullable = false)
    var id: Long = 0

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    var name: ProductTypeEnum? = null
}
