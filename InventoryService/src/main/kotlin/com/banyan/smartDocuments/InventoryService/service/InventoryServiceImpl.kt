package com.banyan.smartDocuments.InventoryService.service

import com.banyan.smartDocuments.InventoryService.dto.InventoryRequest
import com.banyan.smartDocuments.InventoryService.event.OrderPlacedEvent
import com.banyan.smartDocuments.InventoryService.repository.InventoryRepository
import org.slf4j.LoggerFactory
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

    private val logger = LoggerFactory.getLogger(InventoryServiceImpl::class.java)

    override fun processOrder(orderPlacedEvent: OrderPlacedEvent) {
        logger.info("Processing order: ${orderPlacedEvent.orderId} ")
        for (item in orderPlacedEvent.items) {
            logger.info("Processing item: ${item.productId} ")
            val inventory = inventoryRepository.findByProductIdAndProductType(item.productId, item.productType)
            if (inventory == null || inventory.quantity < item.quantity) {
                logger.info("Inventory not found or low on stock for ${item.productType}:${item.productId}")
                // Stock low → auto order from supplier
                supplierService.orderFromSupplier(orderPlacedEvent.orderId, item.productId, item.productType, item.quantity)
                orderStatusProducer.publish(orderPlacedEvent.orderId, "WAITING_FOR_RESTOCK", "Low stock for ${item.productType}:${item.productId}")
            } else {
                logger.info("Inventory found and sufficient for ${item.productType}:${item.productId}")
                // Deduct stock
                inventory.quantity -= item.quantity
                inventoryRepository.save(inventory)
                logger.info("Deducted ${item.quantity} from inventory for ${item.productType}:${item.productId}")
                orderStatusProducer.publish(orderPlacedEvent.orderId, "INVENTORY_CONFIRMED")
            }
        }
    }
}