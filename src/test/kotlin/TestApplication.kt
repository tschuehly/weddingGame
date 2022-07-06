import de.tschuehly.weddingGame.WeddingGameApplication
import de.tschuehly.weddingGame.util.AbstractIntegrationTest

fun main(args: Array<String>) {
    val app = WeddingGameApplication.createSpringApplication()
    app.addInitializers(AbstractIntegrationTest.Initializer())
    app.run()
}
