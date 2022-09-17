package com.twinbits.kafkaspring

import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility.await
import org.junit.jupiter.api.Test
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
import org.springframework.test.context.jdbc.Sql
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
    @Sql("/schema.sql")
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
        @JvmStatic
        @DynamicPropertySource
        fun configureMysql(registry: DynamicPropertyRegistry) {
            val dbPort = System.getProperty("db_1.tcp.3306")
            registry.add("spring.datasource.url"){"jdbc:mysql://localhost:$dbPort/db"}
        }

    }
}