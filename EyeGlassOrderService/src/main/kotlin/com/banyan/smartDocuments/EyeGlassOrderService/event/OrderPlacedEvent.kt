package com.banyan.smartDocuments.EyeGlassOrderService.event

/* * OrderPlacedEvent.kt
 *
 * This file defines the OrderPlacedEvent data class, which represents an event that is published when an order is placed.
 * It includes details about the order such as order ID, customer ID, items in the order, delivery address, payment method,
 * total amount, and the timestamp of when the order was created.
 */
data class OrderPlacedEvent(
    val orderId: Long,
    val customerId: Long,
    val items: List<OrderItemEvent>,
    val deliveryAddress: String,
    val paymentMethod: String,
    val totalAmount: Double,
    val createdAt: String // ISO format (e.g., 2025-08-07T15:30:00)
)