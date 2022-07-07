package de.tschuehly.weddingGame.util

import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.MapPropertySource
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.containers.FixedHostPortGenericContainer
import org.testcontainers.containers.GenericContainer
import org.testcontainers.lifecycle.Startables
import org.testcontainers.utility.DockerImageName
import javax.annotation.PreDestroy

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [DevelopmentInitializer.Initializer::class])

abstract class DevelopmentInitializer {
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
            val postgresContainer = FixedHostPortGenericContainer("postgres:14.4")
                .withEnv("POSTGRES_USER","postgres")
                .withEnv("POSTGRES_PASSWORD","password")
                .withEnv("POSTGRES_DB","postgres")
                .withFixedExposedPort(5432,5432)
                .withReuse(true)


            private val minioContainer = FixedHostPortGenericContainer("minio/minio")
                .withFixedExposedPort(9000,9000)
                .withFixedExposedPort(9001,9001)
                .withReuse(true)
                .withCommand("server /data --console-address :9001")

            val properties: Map<String, String>
                get(){
                    Startables.deepStart(postgresContainer, minioContainer).join()
                    return mapOf(
                        "spring.datasource.url" to
                                "jdbc:postgresql://" + postgresContainer.host + ":5432/postgres",
                        "spring.datasource.password" to "password",
                        "spring.datasource.username" to "postgres",
                        "s3.port" to "9000",
                        "s3.host" to minioContainer.host.toString()
                    )
                }
            @PreDestroy
            fun destroy(){
                postgresContainer.stop()
            }
        }

    }
}
