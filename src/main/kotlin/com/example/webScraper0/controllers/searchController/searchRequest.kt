package com.example.webScraper0.controllers.searchController

data class SearchRequest(
    val rootURLs: List<String>,
    val target: List<String>,
    val limit: Int
)