package com.banyan.smartDocuments.AuthenticationService.service

import com.banyan.smartDocuments.AuthenticationService.model.NewUserRegistrationRequest

interface AuthenticationService {
    fun newUserRegistration(newUserRegistrationRequest: NewUserRegistrationRequest): String
}