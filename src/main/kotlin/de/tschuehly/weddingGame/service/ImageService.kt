package de.tschuehly.weddingGame.service

import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.net.URLEncoder

@Service
class ImageService(
    val uploadService: UploadService
) {
    fun uploadImages(folder: String, fullName: String?, images: Array<MultipartFile>?): String {
        images?.forEach { image ->
            val url = "${
            URLEncoder.encode(
                fullName,
                "UTF-8"
            )
            }_${System.currentTimeMillis()}_${URLEncoder.encode(image.originalFilename, "UTF-8")}"
            runBlocking {
                uploadService.channel.send(Pair(url, image.bytes))
            }
        }
        return "<h2>Bilder erfolgreich hochgeladen</h2>"
    }
}
