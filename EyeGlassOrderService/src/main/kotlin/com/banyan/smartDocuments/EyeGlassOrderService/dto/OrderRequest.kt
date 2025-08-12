package com.banyan.smartDocuments.EyeGlassOrderService.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull


data class OrderRequest(
    @field:NotNull(message = "Customer ID is required")
    val customerId: Long?,

    @field:NotBlank(message = "Delivery address cannot be blank")
    val deliveryAddress: String,

    @field:NotBlank(message = "Payment method cannot be blank")
    val paymentMethod: String,

    @field:NotEmpty(message = "At least one item is required")
    @field:Valid
    val items: List<OrderItemRequest>
)