package com.banyan.smartDocuments.InventoryService.dto

data class InventoryRequest( val productId: Long,
                             val productType: String,
                             val quantity: Int )
