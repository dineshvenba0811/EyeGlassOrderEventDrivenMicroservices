package com.banyan.smartDocuments.AuthenticationService.repository

import com.banyan.smartDocuments.AuthenticationService.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByRoleName(name: String): Role?
}