package com.twinbits.kafkaspring.confs

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TopicsConfiguration {

    @Bean
    fun topic() = NewTopic(Topics.SAMPLE_TOPIC, 1, 1)

}

object Topics {
    const val SAMPLE_TOPIC = "sample-topic"
}