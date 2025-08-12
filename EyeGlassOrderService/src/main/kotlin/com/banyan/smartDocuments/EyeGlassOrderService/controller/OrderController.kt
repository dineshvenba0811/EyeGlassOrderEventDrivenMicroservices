package com.banyan.smartDocuments.EyeGlassOrderService.controller
import com.banyan.smartDocuments.EyeGlassOrderService.dto.OrderRequest
import com.banyan.smartDocuments.EyeGlassOrderService.service.OrderService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
class OrderController (  private val orderService: OrderService) {

    @PostMapping
    fun placeOrder(@Valid @RequestBody orderRequest: OrderRequest): ResponseEntity<String> {
        orderService.placeOrder(orderRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body("Order placed successfully")
    }
}