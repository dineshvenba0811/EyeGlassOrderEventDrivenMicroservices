package com.banyan.smartDocuments.InventoryService.service

import com.banyan.smartDocuments.InventoryService.dto.SupplierOrderRef
import com.banyan.smartDocuments.InventoryService.event.SupplierOrderRequestedEvent
import com.banyan.smartDocuments.InventoryService.listener.OrderConsumer
import com.banyan.smartDocuments.InventoryService.service.SupplierService
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * Service implementation for handling supplier orders via Kafka.
 * This service sends a request to the supplier to fulfill an order for a specific product.
 * It generates a unique reference for the order and sends an event to the Kafka topic "supplier-order-requested".
 * The event includes details such as product ID, product type, requested quantity, and the time the order was requested.
 * The service uses a KafkaTemplate to send the event asynchronously.
 * The reference can be used to track the order status later.
 * * @param kafkaTemplate The Kafka template used to send messages to the Kafka topic.
 *  * @return A unique reference for the supplier order.
 */
@Component
class SupplierServiceKafkaImpl (
    private val kafkaTemplate: KafkaTemplate<String, SupplierOrderRequestedEvent>): SupplierService {

    private val logger = LoggerFactory.getLogger(SupplierServiceKafkaImpl::class.java)


    override fun orderFromSupplier(orderId:Long, productId: Long, productType: String, requiredQuantity: Int): SupplierOrderRef {
        val reference = SupplierOrderRef("SUP-${productId}-${System.currentTimeMillis()}")
        val evt = SupplierOrderRequestedEvent(
            orderId = orderId,
            productId = productId,
            productType = productType,
            requestedQuantity = requiredQuantity,
            requestedAt = LocalDateTime.now().toString()
        )
        logger.info("📦 Sending supplier order request: $evt with reference $reference")
        // Send the event to the Kafka topic "supplier-order-requested"
        // This will trigger the supplier consumer to process the order asynchronously
        kafkaTemplate.send("supplier-order-requested", productId.toString(), evt)
        return reference
    }
}