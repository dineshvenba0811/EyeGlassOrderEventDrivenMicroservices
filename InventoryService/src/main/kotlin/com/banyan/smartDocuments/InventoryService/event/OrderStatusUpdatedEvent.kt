package com.banyan.smartDocuments.InventoryService.event

/**
 * Event representing the update of an order status in the inventory service.
 * This event is published when the status of an order changes, such as when it is confirmed,
 * waiting for restock, completed, or failed.
 *
 * @property orderId The unique identifier of the order.
 * @property status The current status of the order (e.g., PENDING, INVENTORY_CONFIRMED, WAITING_FOR_RESTOCK, COMPLETED, FAILED).
 * @property reason Optional reason for the status change (e.g., if the order failed).
 * @property at The timestamp when the status was updated, in ISO datetime format.
 */
data class OrderStatusUpdatedEvent(
    val orderId: Long,
    val status: String,              // PENDING, INVENTORY_CONFIRMED, WAITING_FOR_RESTOCK, COMPLETED, FAILED
    val reason: String? = null,
    val at: String                   // ISO datetime
)
