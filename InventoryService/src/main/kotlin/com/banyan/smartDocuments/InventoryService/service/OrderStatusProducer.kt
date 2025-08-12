package com.banyan.smartDocuments.InventoryService.service

import com.banyan.smartDocuments.InventoryService.event.OrderStatusUpdatedEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OrderStatusProducer(
    private val kafkaTemplate: KafkaTemplate<String, OrderStatusUpdatedEvent>
) {
    fun publish(orderId: Long, status: String, reason: String? = null) {
        kafkaTemplate.send("order-status-updated",
            orderId.toString(),
            OrderStatusUpdatedEvent(orderId, status, reason, LocalDateTime.now().toString()))
    }
}