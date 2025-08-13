package com.banyan.smartDocuments.api_gateway.config

import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

/**
 * Configuration class for setting up a WebClient with load balancing capabilities.
 * This allows the API Gateway to make requests to other services in a microservices architecture.
 */
@Configuration
class WebClientConfig {
    @Bean
    @LoadBalanced
    fun webClientBuilder(): WebClient.Builder = WebClient.builder()
}