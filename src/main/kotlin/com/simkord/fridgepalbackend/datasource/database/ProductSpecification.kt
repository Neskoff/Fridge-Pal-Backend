package com.simkord.fridgepalbackend.datasource.database

import com.simkord.fridgepalbackend.application.request.ProductFilters
import com.simkord.fridgepalbackend.datasource.database.entity.ProductEntity
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification
import java.time.OffsetDateTime

object ProductSpecification {

    fun filterBy(filters: ProductFilters): Specification<ProductEntity> {
        return Specification { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()

            filters.storedBefore?.let {
                predicates.add(criteriaBuilder.lessThan(root.get("storedDate"), it))
            }

            filters.storedAfter?.let {
                predicates.add(criteriaBuilder.greaterThan(root.get("storedDate"), it))
            }

            filters.expired?.let {
                if (it) {
                    predicates.add(criteriaBuilder.lessThan(root.get("expiryDate"), OffsetDateTime.now()))
                }
            }

            criteriaBuilder.and(*predicates.toList().toTypedArray())
        }
    }
}
