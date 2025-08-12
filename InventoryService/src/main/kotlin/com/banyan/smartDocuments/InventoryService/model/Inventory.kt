package com.banyan.smartDocuments.InventoryService.model
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "inventory")
class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "product_id", nullable = false)
    var productId: Long = 0

    @Column(name = "product_type", nullable = false)
    var productType: String = ""  // Example: "LENS", "FRAME"

    @Column(name = "name")
    var name: String? = null

    @Column(name = "quantity", nullable = false)
    var quantity: Int = 0

    @Column(name = "threshold", nullable = false)
    var threshold: Int = 5  // Auto-reorder when below this

    @Column(name = "restock_level", nullable = false)
    var restockLevel: Int = 10  // Reorder this much when low

    @Column(name = "supplier_id")
    var supplierId: Long? = null

    @Column(name = "last_updated", nullable = false)
    var lastUpdated: LocalDateTime = LocalDateTime.now()
}