package com.example.webScraper0.controllers.searchController

import com.example.webScraper0.services.QueueService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class SearchController(
    private val queueService: QueueService,
) {


    @PostMapping("/search")
    suspend  fun search(@RequestBody request: SearchRequest): ResponseEntity<String> {
        // TODO: call service layer here
        queueService.send(request)

        return ResponseEntity.ok("Queued")
    }
}
