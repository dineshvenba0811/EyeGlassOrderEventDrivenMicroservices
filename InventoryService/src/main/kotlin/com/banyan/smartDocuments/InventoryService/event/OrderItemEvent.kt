package com.banyan.smartDocuments.InventoryService.event

// OrderItemEvent.kt
data class OrderItemEvent(
    val productId: Long,
    val quantity: Int,
    val productType: String // "LENS", "FRAME"
)

