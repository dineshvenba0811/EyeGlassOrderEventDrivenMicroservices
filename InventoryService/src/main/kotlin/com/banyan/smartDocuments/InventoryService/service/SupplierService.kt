package com.banyan.smartDocuments.InventoryService.service

import com.banyan.smartDocuments.InventoryService.dto.SupplierOrderRef

/**
 * Service interface for managing supplier orders.
 */
interface SupplierService {
    fun orderFromSupplier(orderId: Long,productId: Long, productType: String, requiredQuantity: Int): SupplierOrderRef
}