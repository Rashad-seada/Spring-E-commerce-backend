package com.example.api_gateway_server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class ApiGatewayServerApplication

fun main(args: Array<String>) {
	runApplication<ApiGatewayServerApplication>(*args)
}
