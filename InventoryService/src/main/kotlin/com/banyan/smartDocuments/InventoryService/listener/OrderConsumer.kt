package com.banyan.smartDocuments.InventoryService.listener

import com.banyan.smartDocuments.InventoryService.event.OrderPlacedEvent
import com.banyan.smartDocuments.InventoryService.service.InventoryService
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

/**
 * Listener for order events from the Order Service.
 * This service listens to the "order-placed" topic and processes incoming order events.
 * When an order is placed, it checks the inventory and either confirms the order or requests stock from suppliers.
 * It uses the InventoryService to handle the business logic of processing orders.
 * The service is annotated with @Service to indicate that it is a Spring-managed component.
 * The KafkaListener annotation specifies the topic to listen to and the consumer group ID.
 * The consumeOrder method is triggered when a new message is received on the "order-placed" topic.
 * It logs the received event and calls the InventoryService to process the order.
 */
@Service
class OrderConsumer ( private val inventoryService: InventoryService) {

    private val logger = LoggerFactory.getLogger(OrderConsumer::class.java)

    @KafkaListener(topics = ["order-placed"], groupId = "inventory-service-group")
    fun consumeOrder(event: com.banyan.smartDocuments.InventoryService.event.OrderPlacedEvent) {
        logger.info("📦 Received order event: $event")
        inventoryService.processOrder(event)
    }
}