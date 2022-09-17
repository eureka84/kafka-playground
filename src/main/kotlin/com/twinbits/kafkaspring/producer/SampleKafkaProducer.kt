package com.twinbits.kafkaspring.producer

import com.twinbits.kafkaspring.confs.Topics.SAMPLE_TOPIC
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

data class Message(val content: String)

@Component
class SampleKafkaProducer(private val kafkaTemplate: KafkaTemplate<String, Message>) {

    fun send(message: Message) {

        kafkaTemplate.send(SAMPLE_TOPIC, "fake-key", message)
    }

}

