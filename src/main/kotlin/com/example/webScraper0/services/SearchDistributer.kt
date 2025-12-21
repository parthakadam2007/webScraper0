package com.example.webScraper0.services

import com.example.webScraper0.controllers.searchController.SearchRequest
import org.springframework.stereotype.Service


@Service
class SearchDistributer {
    fun resiver(message: SearchRequest) {
        println("scearch distributer"+message)

    }
}