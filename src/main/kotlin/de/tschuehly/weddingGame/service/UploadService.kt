package de.tschuehly.weddingGame.service

import io.minio.MinioClient
import io.minio.PutObjectArgs
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
    @Value("\${S3_ACCESS_KEY}")
    lateinit var s3AccessKey: String
    @Value("\${S3_SECRET_KEY}")
    lateinit var s3SecretKey: String

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
            val (url, image) = urlAndImage
            println("Uploading Image: $url")
            for (i in 0..5) {
                try {
                    minioClient().putObject(
                        PutObjectArgs.builder().bucket("test").`object`(url).stream(image.inputStream(), image.size.toLong(), -1).build()
                    )
                    processedImages += 1
                    println("$processedImages: Uploaded $url")

                    break
                } catch (e: Exception) {
                    println(e.message)
                }
            }
        }.let { println("Das Hochladen brauchte: $it ms") }
    }

    private fun minioClient(): MinioClient {
        return MinioClient.builder()
            .endpoint("http://127.0.0.1:9000")
            .credentials(s3AccessKey, s3SecretKey)
            .build()
    }
    @PreDestroy
    fun cancelScope() {
        coroutineScope.cancel()
    }
}
