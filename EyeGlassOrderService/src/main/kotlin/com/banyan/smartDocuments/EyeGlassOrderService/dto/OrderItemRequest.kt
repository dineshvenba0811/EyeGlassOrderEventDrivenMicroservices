package com.banyan.smartDocuments.EyeGlassOrderService.dto

import com.banyan.smartDocuments.EyeGlassOrderService.enum.ProductType
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

/**
 * OrderItemRequest** is a data class that represents an item in an order request for eyeglasses.
 * It includes fields for the product ID, quantity, and product type.
 * The class uses validation annotations to ensure that the fields meet certain criteria.
 */
data class OrderItemRequest(
    @field:NotNull(message = "Product ID must not be null")
    val productId: Long?,

    @field:Min(value = 1, message = "Quantity must be at least 1")
    val quantity: Int,

    @field:NotNull(message = "Product type must not be blank")
    val productType: ProductType // e.g., "LENS", "FRAME"
)
