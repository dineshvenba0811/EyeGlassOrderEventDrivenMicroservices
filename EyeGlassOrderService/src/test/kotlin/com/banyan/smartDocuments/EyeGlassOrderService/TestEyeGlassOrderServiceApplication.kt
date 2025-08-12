package com.banyan.smartDocuments.EyeGlassOrderService

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<EyeGlassOrderServiceApplication>().with(TestcontainersConfiguration::class).run(*args)
}
