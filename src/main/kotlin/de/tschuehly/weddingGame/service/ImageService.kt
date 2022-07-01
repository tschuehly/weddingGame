package de.tschuehly.weddingGame.service

import io.minio.GetPresignedObjectUrlArgs
import io.minio.MinioClient
import io.minio.http.Method
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URLEncoder
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class ImageService {
    @Value("\${S3_ACCESS_KEY}")
    lateinit var s3AccessKey: String
    @Value("\${S3_SECRET_KEY}")
    lateinit var s3SecretKey: String

    fun getUploadPresignedObjectUrl(uuid: UUID?, fullName: String?, fileName: String): String {

        val objectName = "${uuid ?: "safari" }/${
        URLEncoder.encode(
            fullName,
            "UTF-8"
        )
        }_${System.currentTimeMillis()}_${URLEncoder.encode(fileName, "UTF-8")}"
        return minioClient().getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .method(Method.PUT)
                .bucket("test") //TODO: Replace with weddingID
                .`object`(objectName)
                .expiry(10, TimeUnit.MINUTES)
                .build()
        )
    }

    private fun minioClient(): MinioClient {
        return MinioClient.builder()
            .endpoint("http://127.0.0.1:9000")
            .credentials(s3AccessKey, s3SecretKey)
            .build()
    }
}
