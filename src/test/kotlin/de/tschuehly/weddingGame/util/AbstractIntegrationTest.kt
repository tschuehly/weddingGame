package de.tschuehly.weddingGame.util

import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.MapPropertySource
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.lifecycle.Startables
import javax.annotation.PreDestroy

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [AbstractIntegrationTest.Initializer::class])

abstract class AbstractIntegrationTest {
    class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(context: ConfigurableApplicationContext) {
            val env = context.environment
            env.propertySources.addFirst(
                MapPropertySource(
                    "testcontainers", properties
                )
            )
        }

        companion object {
            private val postgresContainer = PostgreSQLContainer("postgres:14.4")
                .withUsername("postgres")
                .withPassword("password")
                .withDatabaseName("postgres")
                .withExposedPorts(5432)
                .withReuse(true)
            val properties: Map<String, String>
                get(){
                    Startables.deepStart(postgresContainer).join()
                    return mapOf(
                        "spring.datasource.url" to postgresContainer.jdbcUrl,
                        "spring.datasource.password" to postgresContainer.password,
                        "spring.datasource.username" to postgresContainer.username
                    )
                }
            @PreDestroy
            fun destroy(){
                postgresContainer.stop()
            }
        }

    }
}
