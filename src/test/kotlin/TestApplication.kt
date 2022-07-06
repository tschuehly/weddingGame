import de.tschuehly.weddingGame.WeddingGameApplication
import de.tschuehly.weddingGame.util.IntegrationTestBase
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import org.testcontainers.containers.PostgreSQLContainer
import java.net.URI
import java.util.*
import javax.annotation.PreDestroy

class TestApplication {
}
fun main(args: Array<String>) {
    val app = WeddingGameApplication.createSpringApplication()
    app.addInitializers(IntegrationTestBase.Initializer.Initializer())
    app.run()
}
