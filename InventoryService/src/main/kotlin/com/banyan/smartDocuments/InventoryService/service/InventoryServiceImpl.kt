package com.banyan.smartDocuments.InventoryService.service

import com.banyan.smartDocuments.InventoryService.dto.InventoryRequest
import com.banyan.smartDocuments.InventoryService.event.OrderPlacedEvent
import com.banyan.smartDocuments.InventoryService.repository.InventoryRepository
import org.springframework.stereotype.Service

/**
 * Service to handle inventory management.
 * It processes incoming orders, checks stock levels, and manages supplier orders.
 * If stock is low, it automatically orders from suppliers.
 * If stock is sufficient, it deducts the ordered quantity from inventory.
 */
@Service
class InventoryServiceImpl(private val inventoryRepository: InventoryRepository,
                           private val strategyFactory: StockStrategyFactory,
                           private val supplierService: SupplierService,
                           private val orderStatusProducer: OrderStatusProducer ):
    InventoryService {

    override fun processOrder(orderPlacedEvent: OrderPlacedEvent) {

        for (item in orderPlacedEvent.items) {
            val inventory = inventoryRepository.findByProductIdAndProductType(item.productId, item.productType)
            if (inventory == null || inventory.quantity < item.quantity) {
                // Stock low → auto order from supplier
                supplierService.orderFromSupplier(orderPlacedEvent.orderId, item.productId, item.productType, item.quantity)
                orderStatusProducer.publish(orderPlacedEvent.orderId, "WAITING_FOR_RESTOCK", "Low stock for ${item.productType}:${item.productId}")
            } else {
                // Deduct stock
                inventory.quantity -= item.quantity
                inventoryRepository.save(inventory)
                orderStatusProducer.publish(orderPlacedEvent.orderId, "INVENTORY_CONFIRMED")
            }
        }
    }
}