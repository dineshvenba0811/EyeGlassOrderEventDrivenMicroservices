package com.banyan.smartDocuments.AuthenticationService.controller

import com.banyan.smartDocuments.AuthenticationService.model.AuthRequest
import com.banyan.smartDocuments.AuthenticationService.model.NewUserRegistrationRequest
import com.banyan.smartDocuments.AuthenticationService.service.AuthenticationService
import com.banyan.smartDocuments.AuthenticationService.service.AuthenticationServiceImpl
import com.banyan.smartDocuments.AuthenticationService.service.AuthenticationServiceValidator
import com.banyan.smartDocuments.AuthenticationService.util.JwtUtil
import org.slf4j.LoggerFactory
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

/** * AuthenticationController is a REST controller that handles authentication-related requests.
 * It provides endpoints for user login, registration, and token validation.
 * It uses JWT for authentication and authorization.
 */
@RestController
@RequestMapping("/api/auth")
class AuthenticationController(private val authenticationManager: AuthenticationManager,
                               private val jwtUtil: JwtUtil,
                               private val authValidator: AuthenticationServiceValidator,
                               private val authenticationService: AuthenticationService) {

    private val logger = LoggerFactory.getLogger(AuthenticationController::class.java)

    @PostMapping("/login")
    fun login(@RequestBody authRequest: AuthRequest,): Map<String, String> {
        logger.info("Received login request for user ${authRequest.username}")
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val token = jwtUtil.generateToken(authRequest.username)
        logger.info("Generated JWT token for user ${authRequest.username}: $token")
        return mapOf("token" to token)
    }

    @PostMapping("/registerNewUser")
    fun registerNewUser(@RequestBody newUserRegistrationRequest: NewUserRegistrationRequest,): ResponseEntity<String> {
        logger.info("Received registration request for user ${newUserRegistrationRequest.username}")
        val authValidationResponse = authValidator.validateNewUserRegistration(newUserRegistrationRequest)
        if (authValidationResponse) {
            logger.info("Registration request for user ${newUserRegistrationRequest.username} is valid")
            authenticationService.newUserRegistration(newUserRegistrationRequest)
            return ResponseEntity.ok(
                "User ${newUserRegistrationRequest.username} registered successfully with roles ${
                    newUserRegistrationRequest.roles.joinToString(
                        ", "
                    )
                }"
            )
        }
        logger.info("Registration request for user ${newUserRegistrationRequest.username} is invalid")
        return ResponseEntity.badRequest().body("Invalid user registration request")
    }

    @PostMapping("/validateToken")
    fun validateToken(@RequestHeader("Authorization") authHeader: String?): ResponseEntity<String> {
        logger.info("Received token validation request")

        if (authHeader.isNullOrBlank() || !authHeader.startsWith("Bearer ")) {
            logger.error("Missing or invalid Authorization header")
            return ResponseEntity.badRequest().body("Missing or invalid Authorization header")
        }

        val token = authHeader.removePrefix("Bearer ").trim()
        return if (jwtUtil.validateToken(token)) {
            logger.info("Token is valid")
            ResponseEntity.ok("Token is valid")
        } else {
            logger.error("Token is invalid or expired")
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid or expired")
        }
    }
}