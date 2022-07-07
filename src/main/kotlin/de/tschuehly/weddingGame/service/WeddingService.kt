package de.tschuehly.weddingGame.service

import de.tschuehly.weddingGame.model.CustomTheme
import de.tschuehly.weddingGame.model.Wedding
import de.tschuehly.weddingGame.repository.CustomThemeRepository
import de.tschuehly.weddingGame.repository.WeddingRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class WeddingService(
    val weddingRepository: WeddingRepository,
    val customThemeRepository: CustomThemeRepository

    ){
    fun save(wedding: Wedding) = weddingRepository.save(wedding)

    fun getBySubdomain(subdomain: String) = weddingRepository.findBySubdomain(subdomain)

    fun setCustomTheme(weddingId: Long, customTheme: CustomTheme){
        weddingRepository.findByIdOrNull(weddingId)?.let {
            wedding -> wedding.customTheme = customThemeRepository.save(customTheme)
        }
    }
}
