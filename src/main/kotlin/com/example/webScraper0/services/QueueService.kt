package com.example.webScraper0.services

import dev.kourier.amqp.Properties
import dev.kourier.amqp.connection.amqpConfig
import dev.kourier.amqp.connection.createAMQPConnection
import dev.kourier.amqp.channel.AMQPChannel
import dev.kourier.amqp.connection.AMQPConnection
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.CoroutineScope
import org.springframework.context.annotation.Bean
import com.example.webScraper0.controllers.searchController.searchRequest
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.stereotype.Service
import tools.jackson.module.kotlin.jacksonObjectMapper

@Service
class QueueService(
    private val coroutineScope: CoroutineScope,
    private val searchDistributer: SearchDistributer
) {

    private val queueName = "default_queue"
    private val objectMapper = jacksonObjectMapper()

    private lateinit var connection: AMQPConnection
    private lateinit var channel: AMQPChannel

    private val config = amqpConfig {
        server {
            host = "localhost"
        }
    }

    // Setup ONCE
    @PostConstruct
    fun init() {
        coroutineScope.launch {
            connection = createAMQPConnection(coroutineScope, config)
            channel = connection.openChannel()

            channel.queueDeclare(
                queueName,
                durable = false,
                exclusive = false,
                autoDelete = false,
                arguments = emptyMap()
            )

            //Start consumer automatically
            startConsumer()

            println("✅ QueueService initialized")
        }
    }

    private suspend fun waitUntilReady() {
        while (!::channel.isInitialized) {
            delay(50)
        }
    }

    // Producer
    suspend fun send(message: searchRequest) {
        waitUntilReady()

        val payload = objectMapper.writeValueAsBytes(message)

        channel.basicPublish(
           payload,
            exchange = "",
            routingKey = queueName,
            properties = Properties()
        )

        println("📤 Message sent")
    }

    // Background consumer (optional)
    fun startConsumer() {
        coroutineScope.launch {
            waitUntilReady()

            val consumer = channel.basicConsume(queueName, noAck = true)
            for (delivery in consumer) {
                val msg =
                    objectMapper.readValue(
                        delivery.message.body,
                        searchRequest::class.java
                    )

                println("📥 Received: $msg")
                searchDistributer.resiver(msg)
            }
        }
    }


    @PreDestroy
    fun shutdown() {
        coroutineScope.launch {
            if (::channel.isInitialized) channel.close()
            if (::connection.isInitialized) connection.close()
        }
    }
}

