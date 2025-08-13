package com.banyan.smartDocuments.AuthenticationService.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

/**
 * User is an entity class that represents a user in the system.
 * It is mapped to the "Users" table in the database.
 *
 * @property id The unique identifier for the user.
 * @property username The username of the user, which must be unique and not null.
 * @property password The password of the user, which must not be null.
 * @property roles The set of roles assigned to the user, fetched eagerly and persisted with cascade.
 */
@Entity
@Table(name = "Users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, unique = true)
    var username: String = "",

    @Column(nullable = false)
    var password: String = "",

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST])
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: Set<Role> = mutableSetOf()
) {
    constructor() : this(null, "", "", mutableSetOf())
}