import de.tschuehly.weddingGame.WeddingGameApplication
import de.tschuehly.weddingGame.util.DevelopmentInitializer

fun main(args: Array<String>) {
    val app = WeddingGameApplication.createSpringApplication()
    app.addInitializers(DevelopmentInitializer.Initializer())
    app.run(*args)
}
