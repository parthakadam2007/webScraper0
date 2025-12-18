package com.example.webScraper0.config

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CoroutineConfig {

    @Bean
    fun applicationScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO)
    }
}
