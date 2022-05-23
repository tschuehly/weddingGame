package de.tschuehly.weddingGame.service

import com.github.sardine.SardineFactory
import com.github.sardine.impl.SardineException
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.net.URLEncoder
import java.util.*
import javax.annotation.PreDestroy

@Service
class ImageService(
    val uploadService: UploadService
) {
    @Value("\${WEBDAV_PW}")
    lateinit var webdavPw: String

    fun uploadImages(uuid: UUID, fullName: String?, images: Array<MultipartFile>?): String {
        val sardine = SardineFactory.begin("u292326-sub2",webdavPw)
        println(fullName)
        if(!sardine.exists("https://u292326-sub2.your-storagebox.de/$uuid/")){
            sardine.createDirectory("https://u292326-sub2.your-storagebox.de/$uuid/")
        }

        images?.forEach { image ->
                val url = "https://u292326-sub2.your-storagebox.de/$uuid/${URLEncoder.encode(fullName,"UTF-8")}_${System.currentTimeMillis()}_${URLEncoder.encode(image.originalFilename,"UTF-8")}"
                runBlocking {
                    uploadService.channel.send(Pair(url,image.bytes))
                }
        }
        return "<h2>Bilder erfolgreich hochgeladen</h2>"

    }



}
