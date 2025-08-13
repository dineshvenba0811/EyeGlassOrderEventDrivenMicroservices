package com.banyan.smartDocuments.api_gateway.config

import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component

/**
 * Validates if a request is secured or not based on its path.
 * This is used to determine if a request should be authenticated or not.
 * The paths that are considered open (not secured) are defined in the `openApiEndpoints` list.
 */
@Component
class RouteValidator {
    private val openApiEndpoints = listOf(
        "/api/auth/login",
        "/api/auth/registerNewUser",
        "/api/auth/token",
        "/api/auth/validateToken",   // keep public so clients can check tokens
        "/api/public/"
    )

    fun isSecured(request: ServerHttpRequest): Boolean {
        val path = request.uri.path
        // If request starts with any open path, it's NOT secured
        return openApiEndpoints.none { open ->
            path.equals(open, ignoreCase = true) || path.startsWith(open, ignoreCase = true)
        }
    }
}