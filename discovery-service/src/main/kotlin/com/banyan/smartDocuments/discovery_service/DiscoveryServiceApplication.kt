package com.banyan.smartDocuments.discovery_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

/**
 * DiscoveryServiceApplication** is the main application class for the Discovery Service.
 * It is annotated with `@SpringBootApplication` to enable Spring Boot auto-configuration,
 * and `@EnableEurekaServer` to enable the Eureka server functionality.
 * This service acts as a service registry for other microservices in the Banyan Smart Documents ecosystem.
 * It allows microservices to register themselves and discover other services in the system.
 */
@SpringBootApplication
@EnableEurekaServer
class DiscoveryServiceApplication

fun main(args: Array<String>) {
	runApplication<DiscoveryServiceApplication>(*args)
}
