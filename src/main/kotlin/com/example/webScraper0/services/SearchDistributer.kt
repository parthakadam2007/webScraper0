package com.example.webScraper0.services

import com.example.webScraper0.controllers.searchController.SearchRequest
import org.springframework.stereotype.Service
import com.example.webScraper0.services.WebScraperWorker


@Service
class SearchDistributer (
    private val webScraperWorker: WebScraperWorker
){
    fun resiver(message: SearchRequest) {
        println("scearch distributer"+message)
        this.webScraperWorker.setSearchRequest(message);
        val result:List<String> = this.webScraperWorker.startScraping();
        println(result);


    }
}