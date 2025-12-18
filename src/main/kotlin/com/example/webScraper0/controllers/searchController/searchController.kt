package com.example.webScraper0.controllers.searchController

import com.example.webScraper0.controllers.searchController.SearchRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class SearchController {

    @PostMapping("/search")
    fun search(@RequestBody request: SearchRequest): ResponseEntity<String> {
        // TODO: call service layer here
        return ResponseEntity.ok("OK")
    }
}
