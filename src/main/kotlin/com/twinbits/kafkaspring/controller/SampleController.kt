package com.twinbits.kafkaspring.controller

import com.twinbits.kafkaspring.producer.Message
import com.twinbits.kafkaspring.producer.SampleKafkaProducer
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
class SampleController(private val sampleKafkaProducer: SampleKafkaProducer) {

    @PutMapping("/send")
    fun send(): ResponseEntity<Void> {
        sampleKafkaProducer.send(Message("A message sent on ${Instant.now()}"))
        return ResponseEntity.ok().build()
    }

}