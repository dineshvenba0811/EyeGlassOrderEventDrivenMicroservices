package com.banyan.smartDocuments.EyeGlassOrderService.event

// OrderItemEvent.kt
data class OrderItemEvent(
    val productId: Long,
    val quantity: Int,
    val productType: String // "LENS", "FRAME"
)
