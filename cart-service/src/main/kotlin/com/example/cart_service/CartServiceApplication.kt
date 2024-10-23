package com.example.cart_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class CartServiceApplication

fun main(args: Array<String>) {
	runApplication<CartServiceApplication>(*args)
}
