package com.banyan.smartDocuments.InventoryService.listener

import com.banyan.smartDocuments.InventoryService.event.SupplierOrderRequestedEvent
import com.banyan.smartDocuments.InventoryService.event.SupplierOrderUpdatedEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import java.time.LocalDateTime
import kotlin.concurrent.thread

class SupplierConsumer (
    private val kafkaTemplate: KafkaTemplate<String, SupplierOrderUpdatedEvent>
    ) {
    private val logger = LoggerFactory.getLogger(SupplierConsumer::class.java)

        @KafkaListener(topics = ["supplier-order-requested"], groupId = "supplier-service")
        fun onRequested(evt: SupplierOrderRequestedEvent) {
            // simulate async fulfillment
            thread {
                Thread.sleep(1500)
                val update = SupplierOrderUpdatedEvent(
                    orderId = evt.orderId,
                    productId = evt.productId,
                    productType = evt.productType,
                    deliveredQuantity = evt.requestedQuantity,
                    supplierOrderRef = "SIM-${evt.productId}",
                    deliveredAt = LocalDateTime.now().toString()
                )
                logger.info("📦 Supplier order updated: $update")
                kafkaTemplate.send("supplier-order-updated", evt.productId.toString(), update)
            }
        }
    }