package com.banyan.smartDocuments.InventoryService.service

import com.banyan.smartDocuments.InventoryService.event.OrderStatusUpdatedEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * Service for publishing order status updates to Kafka.
 * This service sends an event to the Kafka topic "order-status-updated" whenever an order's status changes.
 * The event includes the order ID, new status, optional reason for the status change, and the timestamp of the update.
 *
 * @param kafkaTemplate The Kafka template used to send messages to the Kafka topic.
 */
@Service
class OrderStatusProducer(
    private val kafkaTemplate: KafkaTemplate<String, OrderStatusUpdatedEvent>
) {
    private val logger = LoggerFactory.getLogger(OrderStatusProducer::class.java)

    fun publish(orderId: Long, status: String, reason: String? = null) {
        logger.info("📦 Publishing order status update for order ID: $orderId, status: $status, reason: $reason")
        kafkaTemplate.send("order-status-updated",
            orderId.toString(),
            OrderStatusUpdatedEvent(orderId, status, reason, LocalDateTime.now().toString()))
    }
}