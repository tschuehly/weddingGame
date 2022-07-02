package de.tschuehly.weddingGame.service

import de.tschuehly.weddingGame.dto.ImageDTO
import de.tschuehly.weddingGame.model.Image
import io.minio.GetPresignedObjectUrlArgs
import io.minio.MinioClient
import io.minio.http.Method
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import de.tschuehly.weddingGame.repository.ImageRepository
import java.net.URLEncoder
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class ImageService(
    private val imageRepository: ImageRepository
) {
    @Value("\${S3_ACCESS_KEY}")
    lateinit var s3AccessKey: String
    @Value("\${S3_SECRET_KEY}")
    lateinit var s3SecretKey: String

    fun getUploadUrl(uuid: UUID?, fileName: String): ImageDTO {
        val objectName = "${uuid ?: "safari" }/${URLEncoder.encode(fileName, "UTF-8")}_${System.currentTimeMillis()}"
        val uploadUrl = minioClient().getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .method(Method.PUT)
                .bucket("test") // TODO: Replace with weddingID
                .`object`(objectName)
                .expiry(10, TimeUnit.MINUTES)
                .build()
        )
        return ImageDTO(uploadUrl,objectName,"","")
    }


    private fun minioClient(): MinioClient {
        return MinioClient.builder()
            .endpoint("http://127.0.0.1:9000")
            .credentials(s3AccessKey, s3SecretKey)
            .build()
    }

    fun getAll(): List<Image> {
        return imageRepository.findAll()
    }

    fun save(imageDTO: ImageDTO){

        val downloadUrl = minioClient().getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket("test") // TODO: Replace with weddingID
                .`object`(imageDTO.objectName)
                .expiry(7, TimeUnit.DAYS)
                .build()
        )
        imageRepository.save(Image(
            null,
            imageDTO.objectName,
            downloadUrl,
            Date.from(Date().toInstant().plus(7, ChronoUnit.DAYS))
        ))
    }
}
