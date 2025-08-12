package com.banyan.smartDocuments.EyeGlassOrderService.repository

import com.banyan.smartDocuments.EyeGlassOrderService.model.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository: JpaRepository<Order, Long> {

}