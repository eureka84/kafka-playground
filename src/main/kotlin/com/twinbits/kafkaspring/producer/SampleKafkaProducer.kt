package com.twinbits.kafkaspring.producer

import com.twinbits.kafkaspring.confs.Topics.SAMPLE_TOPIC
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class SampleKafkaProducer(private val kafkaTemplate: KafkaTemplate<String, String>) {

    fun send(message: String) {

        kafkaTemplate.send(SAMPLE_TOPIC, "fake-key", message)
    }

}

