package com.banyan.smartDocuments.EyeGlassOrderService.dto

import com.banyan.smartDocuments.EyeGlassOrderService.enum.ProductType
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class OrderItemRequest(
    @field:NotNull(message = "Product ID must not be null")
    val productId: Long?,

    @field:Min(value = 1, message = "Quantity must be at least 1")
    val quantity: Int,

    @field:NotNull(message = "Product type must not be blank")
    val productType: ProductType // e.g., "LENS", "FRAME"
)
