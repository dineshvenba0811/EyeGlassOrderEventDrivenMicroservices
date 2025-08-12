package com.banyan.smartDocuments.EyeGlassOrderService.service

import com.banyan.smartDocuments.EyeGlassOrderService.dto.OrderRequest
import com.banyan.smartDocuments.EyeGlassOrderService.enum.ProductType
import com.banyan.smartDocuments.EyeGlassOrderService.event.OrderItemEvent
import com.banyan.smartDocuments.EyeGlassOrderService.event.OrderPlacedEvent
import com.banyan.smartDocuments.EyeGlassOrderService.model.Order
import com.banyan.smartDocuments.EyeGlassOrderService.model.OrderItem
import com.banyan.smartDocuments.EyeGlassOrderService.repository.OrderRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class OrderServiceImpl(  private val orderRepository: OrderRepository,
    private val KafkaTemplate: org.springframework.kafka.core.KafkaTemplate<String, OrderPlacedEvent>
): OrderService {

    private val logger = LoggerFactory.getLogger(OrderServiceImpl::class.java)


    @Transactional
    override fun placeOrder(orderRequest: OrderRequest) {

        val order = Order().apply {
            customerId = orderRequest.customerId!!
            deliveryAddress = orderRequest.deliveryAddress
            paymentMethod = orderRequest.paymentMethod
            createdAt = LocalDateTime.now()
            status = "PENDING"
        }

        var totalAmount = 0.0

        // ✅ Use addItem to ensure bidirectional binding
        orderRequest.items.forEach { itemRequest ->
            val item = OrderItem().apply {
                productId = itemRequest.productId!!
                quantity = itemRequest.quantity
                productType = itemRequest.productType
            }
            order.addItem(item) // ✅ ensures item.order = order + adds to list
            totalAmount += item.quantity * 100.0
        }

        order.totalAmount = totalAmount


        val savedOrder = orderRepository.save(order)
        logger.info("Order saved with ID: {}", savedOrder.id)

        logger.info("Publishing OrderPlacedEvent")

        val event = OrderPlacedEvent(
            orderId = savedOrder.id!!,
            customerId = savedOrder.customerId,
            deliveryAddress = savedOrder.deliveryAddress,
            paymentMethod = savedOrder.paymentMethod,
            totalAmount = savedOrder.totalAmount,
            createdAt = savedOrder.createdAt.toString(),
            items = savedOrder.items.map {
                OrderItemEvent(
                    productId = it.productId,
                    quantity = it.quantity,
                    productType = it.productType.name // Enum to string
                )
            }
        )

        KafkaTemplate.send("order-placed", event)

        logger.info("Order placed event published")

    }
}