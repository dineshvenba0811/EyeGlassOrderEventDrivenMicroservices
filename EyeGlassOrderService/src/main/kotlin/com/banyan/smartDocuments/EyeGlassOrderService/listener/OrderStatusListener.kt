package com.banyan.smartDocuments.EyeGlassOrderService.listener

import com.banyan.smartDocuments.EyeGlassOrderService.dto.OrderStatusUpdatedEvent
import com.banyan.smartDocuments.EyeGlassOrderService.repository.OrderRepository
import com.banyan.smartDocuments.EyeGlassOrderService.service.OrderServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

/**
 * Listener for handling order status updates.
 * This component listens to the "order-status-updated" topic and updates the order status in the database.
 * When an order status is updated, it retrieves the order by ID and updates its status accordingly.
 */
@Component
class OrderStatusListener (private val orderRepository: OrderRepository){

    private val logger = LoggerFactory.getLogger(OrderStatusListener::class.java)


    @KafkaListener(topics = ["order-status-updated"], groupId = "order-service")
    fun onStatus(evt: OrderStatusUpdatedEvent) {
        logger.info("Received event: {}", evt)
        orderRepository.findById(evt.orderId).ifPresent {
            it.status = evt.status
            orderRepository.save(it)
            logger.info("Updated order status to {}", it.status)
        }
    }
}

