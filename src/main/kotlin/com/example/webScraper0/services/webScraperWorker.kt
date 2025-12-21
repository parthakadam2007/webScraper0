package com.example.webScraper0.services

import com.example.webScraper0.controllers.searchController.SearchRequest


class webScraperWorker (
    private var searchRequest: SearchRequest

){
    private val linkQueue: ArrayDeque<String> = ArrayDeque()
    private val visited: MutableSet<String> = mutableSetOf()
    private val results: MutableList<String> = mutableListOf()

    init {
        searchRequest.rootURLs.forEach {
            linkQueue.addLast(it)
        }
    }



    //rest logic






}