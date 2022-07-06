package de.tschuehly.weddingGame.util

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.MapPropertySource
import org.springframework.stereotype.Component
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import java.net.URI
import java.util.*
import javax.annotation.PreDestroy

abstract class IntegrationTestBase {
    companion object Initializer{
        @Container
        val postgresContainer = PostgreSQLContainer<Nothing>("postgres:13.4")
            .apply {
                withDatabaseName("testdb")
                withUsername("duke")
                withPassword("s3crEt")
            }

        var urls =
            mapOf(
                "spring.datasource.url" to postgresContainer::getJdbcUrl,
                "spring.datasource.password" to postgresContainer::getPassword,
                "spring.datasource.username" to postgresContainer::getUsername
            )
        class Initializer: ApplicationContextInitializer<ConfigurableApplicationContext>{
            override fun initialize(applicationContext: ConfigurableApplicationContext) {
                val env = applicationContext.environment
                env.propertySources.addFirst(
                    MapPropertySource(
                        "testcontainers",
                        urls
                    )
                )
            }
        }
    }

}
