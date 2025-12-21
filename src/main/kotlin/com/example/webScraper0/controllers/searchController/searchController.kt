package com.example.webScraper0.controllers.searchController

import com.example.webScraper0.services.QueueService
import com.example.webScraper0.services.SearchDistributer
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class SearchController(
    private val queueService: QueueService,
    private val  searchDistributer: SearchDistributer
) {


    @PostMapping("/search")
    suspend  fun search(@RequestBody request: SearchRequest): ResponseEntity<String> {
        queueService.send(request)

        return ResponseEntity.ok("Queued")
    }
}
