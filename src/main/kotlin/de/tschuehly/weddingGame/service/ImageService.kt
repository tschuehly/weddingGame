package de.tschuehly.weddingGame.service

import de.tschuehly.weddingGame.dto.ImageDTO
import de.tschuehly.weddingGame.model.Image
import io.minio.GetPresignedObjectUrlArgs
import io.minio.MinioClient
import io.minio.http.Method
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import de.tschuehly.weddingGame.repository.ImageRepository
import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
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
    // TODO: Use Subfolder instead of bucketId

    fun getUploadUrl(uuid: UUID?, fileName: String, bucketId: String): ImageDTO {
        createBucketIfNotExists(bucketId)
        val objectName = "${uuid ?: "safari" }/${URLEncoder.encode(fileName, "UTF-8")}_${System.currentTimeMillis()}"
        val uploadUrl = minioClient().getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .method(Method.PUT)
                .bucket(bucketId) // TODO: Replace with weddingID
                .`object`(objectName)
                .expiry(10, TimeUnit.MINUTES)
                .build()
        )
        return ImageDTO(uploadUrl,objectName,"","", bucketId)
    }

    private fun createBucketIfNotExists(bucketId: String){
        if (
            !minioClient().bucketExists(BucketExistsArgs.builder().bucket(bucketId).build())
        ){
            minioClient().makeBucket(
                MakeBucketArgs.builder()
                    .bucket(bucketId)
                    .build());
        }
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

    fun getDownloadUrl(bucketId: String, objectName: String): String{
        createBucketIfNotExists(bucketId)
       return minioClient().getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketId)
                .`object`(objectName)
                .expiry(7, TimeUnit.DAYS)
                .build()
        )
    }

    fun save(imageDTO: ImageDTO){
        imageRepository.save(Image(
            null,
            imageDTO.objectName,
            getDownloadUrl(imageDTO.bucketId,imageDTO.objectName),
            Date.from(Date().toInstant().plus(7, ChronoUnit.DAYS))
        ))
    }
}
