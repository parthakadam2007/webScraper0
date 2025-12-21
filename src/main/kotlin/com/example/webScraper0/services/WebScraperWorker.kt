package com.example.webScraper0.services

import com.example.webScraper0.controllers.searchController.SearchRequest
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import java.net.URI

@Service
class WebScraperWorker(
) {
    private var searchRequest: SearchRequest? = null
    private  var isSearchRequestSet = false

    private var linkQueue: ArrayDeque<String> = ArrayDeque()
    private var visited: MutableSet<String> = mutableSetOf()
    private var results: MutableList<String> = mutableListOf()


    fun setSearchRequest(searchRequest: SearchRequest) {
        this.searchRequest = searchRequest
        this.isSearchRequestSet = true
        this.searchRequest?.rootURLs?.forEach {
            linkQueue.addLast(it)
        }
    }

    fun startScraping(): List<String> {
        if(!isSearchRequestSet) {
            throw error("isSearchRequestSet is not set")
            return emptyList()
        }

        while (linkQueue.isNotEmpty() && visited.size < searchRequest!!.limit) {
            val currentUrl = linkQueue.removeFirst()

            if (currentUrl in visited) continue
            visited.add(currentUrl)

            try {
                val document = fetchPage(currentUrl)
                processPage(document, currentUrl)
                extractLinks(document, currentUrl)

            } catch (e: Exception) {
                println("Failed to scrape $currentUrl : ${e.message}")
            }
        }
        return results
    }

    private fun fetchPage(url: String): Document {
        return Jsoup.connect(url)
            .userAgent("Mozilla/5.0 (WebScraperBot)")
            .timeout(10_000)
            .get()
    }

    private fun processPage(document: Document, url: String) {
        val text = document.body().text().lowercase()
        println("---->"+text)

        for (keyword in searchRequest!!.target) {
            if (text.contains(keyword.lowercase())) {
                results.add("Match found at: $url (keyword: $keyword)")
                break
            }
        }
    }

    private fun extractLinks(document: Document, baseUrl: String) {
        val links = document.select("a[href]")

        for (element in links) {
            val absUrl = element.absUrl("href")
            if (isValidUrl(absUrl) && absUrl !in visited) {
                linkQueue.addLast(absUrl)
            }
        }
    }

    private fun isValidUrl(url: String): Boolean {
        return try {
            val uri = URI(url)
            uri.scheme == "http" || uri.scheme == "https"
        } catch (e: Exception) {
            false
        }
    }
}
