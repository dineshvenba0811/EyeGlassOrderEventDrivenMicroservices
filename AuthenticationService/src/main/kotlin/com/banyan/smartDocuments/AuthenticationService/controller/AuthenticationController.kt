package com.banyan.smartDocuments.AuthenticationService.controller

import com.banyan.smartDocuments.AuthenticationService.model.AuthRequest
import com.banyan.smartDocuments.AuthenticationService.model.NewUserRegistrationRequest
import com.banyan.smartDocuments.AuthenticationService.service.AuthenticationService
import com.banyan.smartDocuments.AuthenticationService.service.AuthenticationServiceValidator
import com.banyan.smartDocuments.AuthenticationService.util.JwtUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthenticationController(private val authenticationManager: AuthenticationManager,
                               private val jwtUtil: JwtUtil,
                               private val authValidator: AuthenticationServiceValidator,
                               private val authenticationService: AuthenticationService) {

    @PostMapping("/login")
    fun login(@RequestBody authRequest: AuthRequest,): Map<String, String> {

        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val token = jwtUtil.generateToken(authRequest.username)
        return mapOf("token" to token)
    }

    @PostMapping("/registerNewUser")
    fun registerNewUser(@RequestBody newUserRegistrationRequest: NewUserRegistrationRequest,): ResponseEntity<String> {
        val authValidationResponse = authValidator.validateNewUserRegistration(newUserRegistrationRequest)
        if (authValidationResponse) {
            authenticationService.newUserRegistration(newUserRegistrationRequest)
            return ResponseEntity.ok(
                "User ${newUserRegistrationRequest.username} registered successfully with roles ${
                    newUserRegistrationRequest.roles.joinToString(
                        ", "
                    )
                }"
            )
        }
        return ResponseEntity.badRequest().body("Invalid user registration request")
    }

    @PostMapping("/validateToken")
    fun validateToken(@RequestHeader("Authorization") authHeader: String?): ResponseEntity<String> {
        if (authHeader.isNullOrBlank() || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Missing or invalid Authorization header")
        }

        val token = authHeader.removePrefix("Bearer ").trim()
        return if (jwtUtil.validateToken(token)) {
            ResponseEntity.ok("Token is valid")
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid or expired")
        }
    }
}