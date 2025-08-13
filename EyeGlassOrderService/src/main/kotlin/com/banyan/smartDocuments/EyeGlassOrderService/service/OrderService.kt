package com.banyan.smartDocuments.EyeGlassOrderService.service

import com.banyan.smartDocuments.EyeGlassOrderService.dto.OrderRequest
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated

/**
 * OrderService** is an interface that defines the contract for placing orders in the eyeglass order service.
 * It includes a method to place an order, which takes an `OrderRequest` object as input.
 */
@Validated
interface OrderService {

    fun placeOrder(@Valid orderRequest: OrderRequest);
}