package de.tschuehly.weddingGame

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["de.tschuehly.weddingGame.repository"])
class WeddingGameApplication

fun main(args: Array<String>) {
    runApplication<WeddingGameApplication>(*args)
}
