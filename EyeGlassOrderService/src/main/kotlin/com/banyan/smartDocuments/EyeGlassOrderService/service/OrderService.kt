package com.banyan.smartDocuments.EyeGlassOrderService.service

import com.banyan.smartDocuments.EyeGlassOrderService.dto.OrderRequest
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated

@Validated
interface OrderService {

    fun placeOrder(@Valid orderRequest: OrderRequest);
}