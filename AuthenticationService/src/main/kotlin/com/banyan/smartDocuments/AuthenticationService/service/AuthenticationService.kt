package com.banyan.smartDocuments.AuthenticationService.service

import com.banyan.smartDocuments.AuthenticationService.model.NewUserRegistrationRequest

/**
 * AuthenticationService** is an interface that defines the contract for user authentication services.
 * It includes methods for user registration and other authentication-related operations.
 * Implementations of this interface will provide the actual logic for these operations.
 */
interface AuthenticationService {
    fun newUserRegistration(newUserRegistrationRequest: NewUserRegistrationRequest): String
}