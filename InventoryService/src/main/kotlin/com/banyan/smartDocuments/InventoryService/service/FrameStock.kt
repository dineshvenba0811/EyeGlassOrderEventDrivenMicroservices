package com.banyan.smartDocuments.InventoryService.service

import com.banyan.smartDocuments.InventoryService.model.Inventory

class FrameStock : StockStrategy  {
    override fun checkStockAndRestock(item: Inventory) {
        if (item.quantity < item.threshold) {
            item.quantity = item.restockLevel
        }
    }
}