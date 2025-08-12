package com.banyan.smartDocuments.InventoryService.service

import com.banyan.smartDocuments.InventoryService.model.Inventory
import org.springframework.stereotype.Component

@Component("LENS")
class LensStock : StockStrategy{
    override fun checkStockAndRestock(item: Inventory) {
        if (item.quantity < item.threshold) {
            item.quantity = item.restockLevel
        }
    }
}