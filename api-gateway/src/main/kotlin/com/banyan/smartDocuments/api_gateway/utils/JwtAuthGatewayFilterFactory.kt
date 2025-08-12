package com.banyan.smartDocuments.api_gateway.utils

import com.banyan.smartDocuments.api_gateway.config.RouteValidator
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class JwtAuthGatewayFilterFactory(
    private val routeValidator: RouteValidator,
    private val webClientBuilder: WebClient.Builder
) : AbstractGatewayFilterFactory<JwtAuthGatewayFilterFactory.Config>(Config::class.java) {

    class Config

    override fun apply(config: Config): GatewayFilter = GatewayFilter { exchange, chain ->
        // same logic as GlobalFilter, but only runs where attached in YAML
        val request = exchange.request
        if (!routeValidator.isSecured(request)) return@GatewayFilter chain.filter(exchange)

        val authHeader = request.headers.getFirst(HttpHeaders.AUTHORIZATION)
        if (authHeader.isNullOrBlank() || !authHeader.startsWith("Bearer ")) {
            exchange.response.statusCode = HttpStatus.UNAUTHORIZED
            return@GatewayFilter exchange.response.setComplete()
        }

        val client = webClientBuilder.build()
        client.post()
            .uri("lb://authenticationservice/api/auth/validateToken")
            .header(HttpHeaders.AUTHORIZATION, authHeader)
            .retrieve()
            .toBodilessEntity()
            .flatMap { chain.filter(exchange) }
            .onErrorResume {
                exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                exchange.response.setComplete()
            }
    }
}