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
import javax.annotation.PostConstruct

@Service
class ImageService(
    private val imageRepository: ImageRepository
) {
    @Value("\${s3.access-key}")
    lateinit var s3AccessKey: String
    @Value("\${s3.secret-key}")
    lateinit var s3SecretKey: String
    @Value("\${s3.bucket-key}")
    lateinit var s3BucketKey: String
    @Value("\${s3.port}")
    lateinit var s3Port: String
    @Value("\${s3.host}")
    lateinit var s3Host: String

    fun getUploadUrl(uuid: UUID?, fileName: String, subfolderId: String): ImageDTO {
        val objectName = "$subfolderId/${uuid ?: "safari" }/${URLEncoder.encode(fileName, "UTF-8")}_${System.currentTimeMillis()}"
        val uploadUrl = minioClient().getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .method(Method.PUT)
                .bucket(s3BucketKey) //
                .`object`(objectName)
                .expiry(10, TimeUnit.MINUTES)
                .build()
        )
        return ImageDTO(uploadUrl,objectName,"","", s3BucketKey)
    }



    fun getAll(): List<Image> {
        return imageRepository.findAll()
    }

    fun getDownloadUrl(objectName: String): String{
       return minioClient().getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(s3BucketKey)
                .`object`(objectName)
                .expiry(7, TimeUnit.DAYS)
                .build()
        )
    }

    fun save(imageDTO: ImageDTO){
        imageRepository.save(Image(
            null,
            imageDTO.objectName,
            getDownloadUrl(imageDTO.objectName),
            Date.from(Date().toInstant().plus(7, ChronoUnit.DAYS))
        ))
    }

    private fun minioClient(): MinioClient {
        return MinioClient.builder()
            .endpoint("http://${s3Host}:$s3Port")
            .credentials(s3AccessKey, s3SecretKey)
            .build()
    }
    @PostConstruct
    private fun createBucketIfNotExists(){
        if (
            !minioClient().bucketExists(BucketExistsArgs.builder().bucket(s3BucketKey).build())
        ){
            minioClient().makeBucket(
                MakeBucketArgs.builder()
                    .bucket(s3BucketKey)
                    .build())
        }
    }

}
