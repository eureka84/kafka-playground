package com.twinbits.kafkaspring

import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility.await
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import java.net.URI
import java.time.Duration

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebMvc
class SampleControllerTest {

    @LocalServerPort
    var localServerPort: Int = 0

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    private val testRestTemplate: TestRestTemplate = TestRestTemplate()

    @Test
    internal fun `send message`() {
        val requestEntity = RequestEntity<Void>(HttpMethod.PUT, URI.create("http://localhost:$localServerPort/send"))

        testRestTemplate.exchange(requestEntity, Void::class.java)

        await()
            .atMost(Duration.ofSeconds(5))
            .untilAsserted {
                assertThat(jdbcTemplate.queryForObject("Select count(*) from samples", Long::class.java))
                    .isGreaterThan(0)
            }
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(SampleControllerTest::class.java)

        @JvmStatic
        @DynamicPropertySource
        fun configureMysql(registry: DynamicPropertyRegistry) {
            val dbPort = System.getProperty("db_1.tcp.3306")
            registry.add("spring.datasource.url") { "jdbc:mysql://localhost:$dbPort/db" }

            val brokerPort = System.getProperty("broker.tcp.9092")
            LOGGER.info("Dynamic kafka port: $brokerPort")
            registry.add("spring.kafka.bootstrap-servers") { "localhost:$brokerPort" }
            registry.add("spring.kafka.consumer.bootstrap-servers") { "localhost:$brokerPort" }
        }

    }
}