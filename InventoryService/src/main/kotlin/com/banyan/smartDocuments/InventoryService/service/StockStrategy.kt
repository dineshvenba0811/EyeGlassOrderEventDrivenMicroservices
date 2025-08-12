package com.banyan.smartDocuments.InventoryService.service

import com.banyan.smartDocuments.InventoryService.model.Inventory

interface StockStrategy {
    fun checkStockAndRestock(item: Inventory)
}