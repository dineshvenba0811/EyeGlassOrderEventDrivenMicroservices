package com.banyan.smartDocuments.AuthenticationService.mapper

import com.banyan.smartDocuments.AuthenticationService.dto.UserDTO
import com.banyan.smartDocuments.AuthenticationService.model.User

/**
 * UserMapper is a utility class that provides methods to convert User entities to UserDTOs.
 * It is used to map the User model to a data transfer object (DTO) for API responses.
 */
class UserMapper {
    fun User.toDto(): UserDTO =
        UserDTO(
            id = this.id,
            username = this.username,
            roles = this.roles.map { it.roleName }.toSet()
        )
}