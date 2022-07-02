package de.tschuehly.weddingGame.repository

import de.tschuehly.weddingGame.model.Image
import de.tschuehly.weddingGame.model.WebsiteUser
import de.tschuehly.weddingGame.model.Wedding
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository : JpaRepository<Image, Long>
interface WeddingRepository : JpaRepository<Wedding, Long>
interface WebsiteUserRepository : JpaRepository<WebsiteUser, Long>{
    fun findByGoogleId(googleId: String) : WebsiteUser?
    fun findByFacebookId(facebookId: String): WebsiteUser?
}
