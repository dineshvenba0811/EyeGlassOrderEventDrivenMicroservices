package com.banyan.smartDocuments.EyeGlassOrderService

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class EyeGlassOrderServiceApplication

fun main(args: Array<String>) {
	runApplication<EyeGlassOrderServiceApplication>(*args)
}
