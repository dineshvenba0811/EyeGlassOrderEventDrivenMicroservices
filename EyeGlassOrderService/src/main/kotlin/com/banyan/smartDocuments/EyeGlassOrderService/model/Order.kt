package com.banyan.smartDocuments.EyeGlassOrderService.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var customerId: Long = 0

    @Column(nullable = false)
    var deliveryAddress: String = ""

    @Column(nullable = false)
    var paymentMethod: String = ""

    @Column(nullable = false)
    var totalAmount: Double = 0.0

    @Column(nullable = false)
    var status: String = "PENDING"

    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    var items: MutableList<OrderItem> = mutableListOf()

    fun addItem(item: OrderItem) {
        item.order = this
        items.add(item)
    }
}