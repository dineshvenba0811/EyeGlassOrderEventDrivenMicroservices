package com.banyan.smartDocuments.AuthenticationService.service

import com.banyan.smartDocuments.AuthenticationService.model.NewUserRegistrationRequest
import com.banyan.smartDocuments.AuthenticationService.repository.RoleRepository
import com.banyan.smartDocuments.AuthenticationService.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

/**
 * Service implementation for handling user authentication and registration.
 * This service provides methods for registering new users and managing user roles.
 */
@Service
class AuthenticationServiceImpl (private val userRepository: UserRepository,
                                 private val roleRepository: RoleRepository,
                                 private val passwordEncoder: PasswordEncoder) : AuthenticationService {

    override fun newUserRegistration(newUserRegistrationRequest: NewUserRegistrationRequest): String {

        // Check if the user already exists
        val existingUser = userRepository.findByUsername(newUserRegistrationRequest.username)
        if (existingUser != null) {
            throw RuntimeException("User with username ${newUserRegistrationRequest.username} already exists")
        }
        // Create a new user entity
        // In a real application, you would also handle password encoding

        val newUser = com.banyan.smartDocuments.AuthenticationService.model.User(
            username = newUserRegistrationRequest.username,
            password = passwordEncoder.encode(newUserRegistrationRequest.password),
            roles = newUserRegistrationRequest.roles.map { roleName ->
                roleRepository.findByRoleName(roleName) ?: throw RuntimeException("Role $roleName not found")
            }.toSet()
        )
        // Save the new user to the repository
        userRepository.save(newUser)

        return "User ${newUserRegistrationRequest.username} registered successfully with roles ${newUserRegistrationRequest.roles.joinToString(", ")}"
    }


}