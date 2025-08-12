package com.banyan.smartDocuments.InventoryService.service

import org.springframework.stereotype.Component

/**
 * Factory for creating stock strategies based on product type.
 */
@Component
class StockStrategyFactory(private val strategies: Map<String, StockStrategy>) {

    fun getStrategy(productType: String): StockStrategy {
        return strategies[productType.uppercase()]
            ?: throw IllegalArgumentException("No strategy for type: $productType")
    }

}