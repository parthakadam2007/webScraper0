package com.example.webScraper0.services

import com.example.webScraper0.controllers.searchController.searchRequest
import org.springframework.stereotype.Service


@Service
class SearchDistributer {
    fun resiver(message: searchRequest) {
        println("scearch distributer"+message)
    }
}