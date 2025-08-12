package com.banyan.smartDocuments.InventoryService.event

data class SupplierOrderRequestedEvent(
    val orderId: Long,
    val productId: Long,
    val productType: String,    // "LENS" | "FRAME"
    val requestedQuantity: Int,
    val requestedAt: String     // ISO string
)
