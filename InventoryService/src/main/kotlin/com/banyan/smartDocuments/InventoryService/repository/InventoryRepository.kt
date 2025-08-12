package com.banyan.smartDocuments.InventoryService.repository

import com.banyan.smartDocuments.InventoryService.model.Inventory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface InventoryRepository: JpaRepository<Inventory, Long> {
    fun findByProductIdAndProductType(productId: Long, productType: String): Inventory?
}