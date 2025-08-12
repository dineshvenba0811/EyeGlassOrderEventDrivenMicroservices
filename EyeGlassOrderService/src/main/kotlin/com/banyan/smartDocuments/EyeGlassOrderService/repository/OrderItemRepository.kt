package com.banyan.smartDocuments.EyeGlassOrderService.repository

import com.banyan.smartDocuments.EyeGlassOrderService.model.OrderItem
import org.springframework.data.jpa.repository.JpaRepository

interface OrderItemRepository : JpaRepository<OrderItem, Long> {
}