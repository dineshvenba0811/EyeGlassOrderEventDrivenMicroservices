package com.banyan.smartDocuments.AuthenticationService.mapper

import com.banyan.smartDocuments.AuthenticationService.dto.UserDTO
import com.banyan.smartDocuments.AuthenticationService.model.User

class UserMapper {
    fun User.toDto(): UserDTO =
        UserDTO(
            id = this.id,
            username = this.username,
            roles = this.roles.map { it.roleName }.toSet()
        )
}