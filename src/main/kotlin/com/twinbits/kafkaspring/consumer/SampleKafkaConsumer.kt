package com.twinbits.kafkaspring.consumer

import com.twinbits.kafkaspring.confs.Topics.SAMPLE_TOPIC
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class SampleKafkaConsumer(private val jdbcTemplate: JdbcTemplate) {

    @KafkaListener(id = "consumer-group", topics = [SAMPLE_TOPIC])
    fun receiveMessage(message: String){
        jdbcTemplate.update("INSERT INTO samples (message) VALUES (?)") { ps ->
            ps.setString(1, message)
        }
    }

}