package com.example.webScraper0.controllers.searchController

data class searchRequest(
    val rootURLs: List<String>,
    val target: List<String>,
    val limit: Int
)