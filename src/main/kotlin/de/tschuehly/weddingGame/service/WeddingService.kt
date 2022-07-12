package de.tschuehly.weddingGame.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.tschuehly.weddingGame.model.CustomTheme
import de.tschuehly.weddingGame.model.Wedding
import de.tschuehly.weddingGame.repository.CustomThemeRepository
import de.tschuehly.weddingGame.repository.WeddingRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class WeddingService(
    val weddingRepository: WeddingRepository,
    val customThemeRepository: CustomThemeRepository,
    val imageService: ImageService

    ){
    fun save(wedding: Wedding) = weddingRepository.save(wedding)

    fun getBySubdomain(subdomain: String) = weddingRepository.findBySubdomain(subdomain)

    fun findById(id: Long) = weddingRepository.findByIdOrNull(id)

    fun setCustomTheme(weddingId: Long, customThemeString: String, coverImageFile: MultipartFile?): Wedding {
        val customTheme : CustomTheme = jacksonObjectMapper().readValue(customThemeString)
        val wedding = weddingRepository.findByIdOrNull(weddingId)
            ?: throw NoSuchElementException("No wedding with Id: $weddingId found")
        wedding.theme = "custom"
        val objectName = "${wedding.folderId}/static/coverImage"

        wedding.customTheme?.coverImage?.let {
            wedding.customTheme?.coverImage = null
            weddingRepository.save(wedding)
            imageService.deleteImage(it)
        }
        coverImageFile?.let {
            customTheme.coverImage = imageService.uploadImageAndSave(objectName,it)
        }
        wedding.customTheme = customThemeRepository.save(customTheme)
        return weddingRepository.save(wedding)
    }

    fun setTheme(weddingId: Long, theme: String): Wedding {
        val wedding = weddingRepository.findByIdOrNull(weddingId)
            ?: throw NoSuchElementException("No wedding with ID: $weddingId found")
        wedding.theme = theme
        wedding.customTheme?.let {customTheme ->
            customTheme.coverImage?.let {
                imageService.deleteImage(it)
            }
            wedding.customTheme = null
            customThemeRepository.delete(customTheme)
        }
        return weddingRepository.save(wedding)
    }
}
