package com.twinbits.kafkaspring.consumer

import com.twinbits.kafkaspring.confs.Topics.SAMPLE_TOPIC
import com.twinbits.kafkaspring.producer.Message
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class SampleKafkaConsumer(private val jdbcTemplate: JdbcTemplate) {

    @KafkaListener(topics = [SAMPLE_TOPIC])
    fun receiveMessage(message: Message){
        jdbcTemplate.update("INSERT INTO samples (message) VALUE ?") { ps ->
            ps.setString(1, message.content)
        }
    }

}