package com.banyan.smartDocuments.EyeGlassOrderService.model
import com.banyan.smartDocuments.EyeGlassOrderService.enum.ProductType
import jakarta.persistence.*

@Entity
@Table(name = "order_items")
class OrderItem{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var productId: Long = 0

    @Enumerated(EnumType.STRING) // Store enum as string (e.g., "LENS", "FRAME")
    @Column(nullable = false)
    var productType: ProductType = ProductType.LENS

    @Column(nullable = false)
    var quantity: Int = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    var order: Order? = null
}