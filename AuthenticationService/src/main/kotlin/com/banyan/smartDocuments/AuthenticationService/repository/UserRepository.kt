package com.banyan.smartDocuments.AuthenticationService.repository

import com.banyan.smartDocuments.AuthenticationService.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * UserRepository is a Spring Data JPA repository interface for managing User entities.
 * It provides methods to perform CRUD operations on the Users table in the database.
 *
 * @property findByUsername A method to find a User by their username.
 */
@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}