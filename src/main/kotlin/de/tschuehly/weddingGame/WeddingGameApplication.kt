package de.tschuehly.weddingGame

import kotlinx.coroutines.runBlocking
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WeddingGameApplication

fun main(args: Array<String>) {
		runApplication<WeddingGameApplication>(*args)
}
