package com.banyan.smartDocuments.AuthenticationService.service

import com.banyan.smartDocuments.AuthenticationService.model.NewUserRegistrationRequest
import org.springframework.stereotype.Component

/**
 * AuthenticationServiceValidator** is a component that validates user registration requests.
 * It checks the validity of the username, password, and roles in the registration request.
 */
@Component
class AuthenticationServiceValidator {
    /**
     * Validates the new user registration request.
     */
    fun validateNewUserRegistration(request: NewUserRegistrationRequest): Boolean {
        return validateUsername(request.username) &&
               validatePassword(request.password) &&
               validateRoles(request.roles)
    }

    fun validateUsername(username: String): Boolean {
        return username.isNotBlank() && username.length in 3..20
    }

    fun validatePassword(password: String): Boolean {
        return password.isNotBlank() && password.length >= 8
    }

    fun validateRoles(roles: Set<String>): Boolean {
        return roles.isNotEmpty() && roles.all { it.isNotBlank() }
    }
}