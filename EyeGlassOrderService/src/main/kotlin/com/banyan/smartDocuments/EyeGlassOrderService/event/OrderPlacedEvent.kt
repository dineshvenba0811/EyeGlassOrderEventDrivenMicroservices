package com.banyan.smartDocuments.EyeGlassOrderService.event

data class OrderPlacedEvent(
    val orderId: Long,
    val customerId: Long,
    val items: List<OrderItemEvent>,
    val deliveryAddress: String,
    val paymentMethod: String,
    val totalAmount: Double,
    val createdAt: String // ISO format (e.g., 2025-08-07T15:30:00)
)