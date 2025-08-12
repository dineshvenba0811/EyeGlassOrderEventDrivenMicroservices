package com.banyan.smartDocuments.AuthenticationService.model

import jakarta.persistence.Entity
import jakarta.persistence.*

@Entity
@Table(name = "Roles")
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, unique = true)
    var roleName: String = ""
) {
    // No-arg constructor for JPA
    constructor() : this(null, "")
}