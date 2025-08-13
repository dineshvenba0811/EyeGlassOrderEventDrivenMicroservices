package com.banyan.smartDocuments.InventoryService.listener

import com.banyan.smartDocuments.InventoryService.event.SupplierOrderUpdatedEvent
import com.banyan.smartDocuments.InventoryService.repository.InventoryRepository
import com.banyan.smartDocuments.InventoryService.service.OrderStatusProducer
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

/**
 * Consumer for handling supplier order updates.
 * This component listens to the "supplier-order-updated" topic and updates the inventory accordingly.
 * When a supplier order is updated, it increases the inventory quantity for the specified product.
 */
@Component
class SupplierUpdateConsumer (
    private val inventoryRepository: InventoryRepository,
    private val orderStatusProducer: OrderStatusProducer
) {
    private val logger = LoggerFactory.getLogger(SupplierUpdateConsumer::class.java)

    @KafkaListener(topics = ["supplier-order-updated"], groupId = "inventory-service")
    fun onSupplierUpdated(evt: SupplierOrderUpdatedEvent) {
        logger.info("📦 Supplier order updated: $evt")
        val inv = inventoryRepository.findByProductIdAndProductType(evt.productId, evt.productType)
            ?: return
        inv.quantity += evt.deliveredQuantity
        inventoryRepository.save(inv)
        logger.info("📦 Inventory updated for ${evt.productType}:${evt.productId}, new quantity: ${inv.quantity}")
        // Publish order status as completed
        logger.info("📦 Publishing order status as COMPLETED for order ID: ${evt.orderId}")
        orderStatusProducer.publish(evt.orderId, "COMPLETED")
    }
}