package de.tschuehly.weddingGame.service

import com.github.sardine.SardineFactory
import com.github.sardine.impl.SardineException
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import kotlin.system.measureTimeMillis

@Service
class UploadService() {
    @Value("\${WEBDAV_PW}")
    lateinit var webdavPw: String

    val channel = Channel<Pair<String, ByteArray>>(10000)

    var processedImages = 0

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    @OptIn(ExperimentalCoroutinesApi::class)
    private val superVisorScope = CoroutineScope(SupervisorJob() + Dispatchers.IO.limitedParallelism(5))
    @PostConstruct
    fun createWorkerGroup() {
        coroutineScope.launch {
            superVisorScope.launch {
                channel.consumeEach {
                    launch {
                        uploadImage(it)
                    }
                }
            }
        }
        coroutineScope.cancel()
    }

    private fun uploadImage(urlAndImage: Pair<String, ByteArray>) {
        measureTimeMillis {
            var (url, image) = urlAndImage
            println("Uploading Image: $url")
            val sardine = SardineFactory.begin("u292326-sub2", webdavPw)
            for (i in 0..5) {
                try {
                    sardine.put(url, image)
                    processedImages += 1
                    println("$processedImages: Uploaded $url")
                    image = ByteArray(0)

                    break
                } catch (e: SardineException) {
                    if (e.statusCode == 408) {
                        println(e)
                    }
                }
            }
        }.let { println("Das Hochladen brauchte: $it ms") }
    }

    @PreDestroy
    fun cancelScope() {
        coroutineScope.cancel()
    }
}
