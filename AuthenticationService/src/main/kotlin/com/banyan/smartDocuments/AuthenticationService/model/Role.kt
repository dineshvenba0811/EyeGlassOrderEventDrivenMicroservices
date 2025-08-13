package com.banyan.smartDocuments.AuthenticationService.model

import jakarta.persistence.Entity
import jakarta.persistence.*

/* * Role is an entity class that represents a user role in the system.
 * It is mapped to the "Roles" table in the database.
 *
 * @property id The unique identifier for the role.
 * @property roleName The name of the role, which must be unique and not null.
 */
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