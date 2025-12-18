package com.example.webScraper0.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheck{

    @GetMapping("/health")
    fun health(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(
            mapOf("status" to "UP")
        )
    }

    @GetMapping("/")
    fun index(): ResponseEntity<String> {
        return ResponseEntity.ok(
            "Welcome to webScraper0"
        )
    }

}