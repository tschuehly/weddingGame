package de.tschuehly.weddingGame.service

import com.github.sardine.SardineFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ImageService {
    fun uploadImages(id: Long, name: String?, images: Array<MultipartFile>?): String {
        val sardine = SardineFactory.begin("u292326","yipPE6uIylg7zY1G")
        println(name)
        if(!sardine.exists("https://u292326.your-storagebox.de/$id/")){
            sardine.createDirectory("https://u292326.your-storagebox.de/$id/")
        }
        images?.forEach { image ->
            sardine.put("https://u292326.your-storagebox.de/$id/${name}_${System.currentTimeMillis()}_${image.originalFilename}", image.bytes)
        }
        return "success"
    }
}
