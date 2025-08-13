package com.banyan.smartDocuments.AuthenticationService.repository

import com.banyan.smartDocuments.AuthenticationService.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * RoleRepository is a Spring Data JPA repository interface for managing Role entities.
 * It provides methods to perform CRUD operations on the Roles table in the database.
 *
 * @property findByRoleName A method to find a Role by its name.
 */
@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByRoleName(name: String): Role?
}