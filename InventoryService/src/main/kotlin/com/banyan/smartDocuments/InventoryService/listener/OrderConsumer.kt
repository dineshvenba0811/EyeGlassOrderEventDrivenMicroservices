package com.banyan.smartDocuments.InventoryService.listener

import com.banyan.smartDocuments.InventoryService.event.OrderPlacedEvent
import com.banyan.smartDocuments.InventoryService.service.InventoryService
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class OrderConsumer ( private val inventoryService: InventoryService) {

    private val logger = LoggerFactory.getLogger(OrderConsumer::class.java)

    @KafkaListener(topics = ["order-placed"], groupId = "inventory-service-group")
    fun consumeOrder(event: com.banyan.smartDocuments.InventoryService.event.OrderPlacedEvent) {
        logger.info("📦 Received order event: $event")
        inventoryService.processOrder(event)
    }
}