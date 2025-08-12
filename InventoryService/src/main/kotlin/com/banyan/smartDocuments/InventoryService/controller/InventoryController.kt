/*
package com.banyan.smartDocuments.InventoryService.controller

import com.banyan.smartDocuments.InventoryService.dto.InventoryRequest
import com.banyan.smartDocuments.InventoryService.service.InventoryService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/inventory")
class InventoryController (private val inventoryService: InventoryService) {

    @PostMapping("/process")
    fun processInventory(@RequestBody request: InventoryRequest): String {
        inventoryService.processInventory(request)
        return "Inventory processed successfully"
    }

}*/
