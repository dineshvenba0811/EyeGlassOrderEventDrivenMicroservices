package com.banyan.smartDocuments.InventoryService

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class InventoryServiceApplication

fun main(args: Array<String>) {
	runApplication<InventoryServiceApplication>(*args)
}
