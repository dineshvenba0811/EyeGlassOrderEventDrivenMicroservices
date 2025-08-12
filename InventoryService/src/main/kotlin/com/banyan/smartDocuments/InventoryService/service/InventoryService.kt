package com.banyan.smartDocuments.InventoryService.service

import com.banyan.smartDocuments.InventoryService.dto.InventoryRequest
import com.banyan.smartDocuments.InventoryService.event.OrderPlacedEvent

interface InventoryService {
    fun processOrder(orderPlacedEvent: OrderPlacedEvent)
}