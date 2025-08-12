package com.banyan.smartDocuments.InventoryService.event

data class SupplierOrderUpdatedEvent (
                                        val orderId: Long,
                                        val productId: Long,
                                        val productType: String,
                                        val deliveredQuantity: Int,
                                        val supplierOrderRef: String,
                                        val deliveredAt: String)