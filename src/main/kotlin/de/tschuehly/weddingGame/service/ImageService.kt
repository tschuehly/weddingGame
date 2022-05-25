package de.tschuehly.weddingGame.service

import com.github.sardine.SardineFactory
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.net.URLEncoder

@Service
class ImageService(
    val uploadService: UploadService
) {
    @Value("\${WEBDAV_PW}")
    lateinit var webdavPw: String

    val storageBoxUrl = "https://u292326-sub2.your-storagebox.de"

    fun uploadImages(folder: String, fullName: String?, images: Array<MultipartFile>?): String {
        val sardine = SardineFactory.begin("u292326-sub2",webdavPw)
        if(!sardine.exists("$storageBoxUrl/$folder/")){
            sardine.createDirectory("$storageBoxUrl/$folder/")
        }

        images?.forEach { image ->
                val url = "$storageBoxUrl/$folder/${URLEncoder.encode(fullName,"UTF-8")}_${System.currentTimeMillis()}_${URLEncoder.encode(image.originalFilename,"UTF-8")}"
                runBlocking {
                    uploadService.channel.send(Pair(url,image.bytes))
                }
        }
        return "<h2>Bilder erfolgreich hochgeladen</h2>"
    }
}
