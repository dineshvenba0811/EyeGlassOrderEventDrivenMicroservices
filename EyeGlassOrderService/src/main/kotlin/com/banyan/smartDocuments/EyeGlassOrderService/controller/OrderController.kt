package com.banyan.smartDocuments.EyeGlassOrderService.controller
import com.banyan.smartDocuments.EyeGlassOrderService.dto.OrderRequest
import com.banyan.smartDocuments.EyeGlassOrderService.service.OrderService
import com.banyan.smartDocuments.EyeGlassOrderService.service.OrderServiceImpl
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * OrderController** is a REST controller that handles HTTP requests related to placing orders for eyeglasses.
 * It provides an endpoint to place an order by accepting an `OrderRequest` object.
 */
@RestController
@RequestMapping("/api/orders")
class OrderController (  private val orderService: OrderService) {


    private val logger = LoggerFactory.getLogger(OrderController::class.java)

    @PostMapping
    fun placeOrder(@Valid @RequestBody orderRequest: OrderRequest): ResponseEntity<String> {
        logger.info("Received order request: {}", orderRequest)
        orderService.placeOrder(orderRequest)
        logger.info("Order placed successfully")
        return ResponseEntity.status(HttpStatus.CREATED).body("Order placed successfully")
    }
}