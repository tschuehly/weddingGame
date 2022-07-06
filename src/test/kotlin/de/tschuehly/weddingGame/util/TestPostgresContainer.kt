package de.tschuehly.weddingGame.util

import org.springframework.stereotype.Component
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class TestPostgresContainer {

    init {
        postgresContainer.start()
    }

    @PreDestroy
    fun destroy() {
        postgresContainer.stop()
    }
    companion object {
        @Container
        val postgresContainer = PostgreSQLContainer<Nothing>("postgres:13.7")
            .apply {
                withDatabaseName("testdb")
                withUsername("duke")
                withPassword("s3crEt")
            }
        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresContainer::getJdbcUrl)
            registry.add("spring.datasource.password", postgresContainer::getPassword)
            registry.add("spring.datasource.username", postgresContainer::getUsername)
        }
    }
}
