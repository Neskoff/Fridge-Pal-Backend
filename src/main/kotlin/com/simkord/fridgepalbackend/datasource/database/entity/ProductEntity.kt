package com.simkord.fridgepalbackend.datasource.database.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "products")
class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productIdGenerator")
    @SequenceGenerator(
        name = "productIdGenerator",
        sequenceName = "product_id_sequence",
        allocationSize = 1,
    )
    @Column(name = "id", nullable = false)
    var id: Long = 0

    @Column(name = "name", nullable = false)
    var name: String = ""

    @ManyToOne
    @JoinColumn(name = "product_type_id")
    var productType: ProductTypeEntity? = null

    @Column(name = "quantity", nullable = false)
    var quantity: Double = 0.0

    @ManyToOne
    @JoinColumn(name = "quantity_unit_id")
    var quantityUnit: QuantityUnitEntity? = null

    @Column(name = "stored_date", nullable = false)
    var storedDate: LocalDate = LocalDate.now()

    @Column(name = "expiry_date", nullable = false)
    var expiryDate: LocalDate = LocalDate.now()

    @Column(name = "image_url", nullable = true)
    var imageUrl: String? = ""

    @Column(name = "image_id", nullable = true)
    var imageId: String? = ""

    fun isExpired(): Boolean {
        return expiryDate.isBefore(LocalDate.now())
    }
}
