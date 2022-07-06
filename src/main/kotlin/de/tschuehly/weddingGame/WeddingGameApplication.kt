package de.tschuehly.weddingGame

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["de.tschuehly.weddingGame.repository"])
class WeddingGameApplication {
    companion object {
        @JvmStatic
        fun createSpringApplication(): SpringApplication {
            return SpringApplication(WeddingGameApplication::class.java)
        }
    }


}

fun main(args: Array<String>) {
    runApplication<WeddingGameApplication>(*args)
}
