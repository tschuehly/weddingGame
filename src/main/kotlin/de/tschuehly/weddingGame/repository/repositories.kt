package de.tschuehly.weddingGame.repository

import de.tschuehly.weddingGame.model.Image
import de.tschuehly.weddingGame.model.Wedding
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository : JpaRepository<Image, Long>
interface WeddingRepository : JpaRepository<Wedding, Long>
