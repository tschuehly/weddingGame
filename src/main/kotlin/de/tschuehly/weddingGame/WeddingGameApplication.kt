package de.tschuehly.weddingGame

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import java.net.URI
import java.util.*

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["de.tschuehly.weddingGame.repository"])
class WeddingGameApplication {


    companion object {
        fun createSpringApplication(): SpringApplication {
            return SpringApplication(WeddingGameApplication::class.java)
        }
    }


}

fun main(args: Array<String>) {
    val props = Properties()

    System.getenv()["DATABASE_URL"]?.let {
        println("DB URL IST:  ******* $it")
        val dbUri = URI(it)
        props["spring.jdbc.url"] = "jdbc:postgresql://" + dbUri.host + dbUri.path
        props["spring.jdbc.username"] = dbUri.userInfo.split(":")[0]
        props["spring.jdbc.password"] = dbUri.userInfo.split(":")[1]
    }

    runApplication<WeddingGameApplication>(*args) {
        setDefaultProperties(props)
    }
}
