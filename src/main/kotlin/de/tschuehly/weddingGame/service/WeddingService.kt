package de.tschuehly.weddingGame.service

import de.tschuehly.weddingGame.model.Wedding
import de.tschuehly.weddingGame.repository.WeddingRepository
import org.springframework.stereotype.Service

@Service
class WeddingService(
    val weddingRepository: WeddingRepository
){
    fun save(wedding: Wedding) = weddingRepository.save(wedding)
}
