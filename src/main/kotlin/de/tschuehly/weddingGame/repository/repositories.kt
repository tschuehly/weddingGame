package de.tschuehly.weddingGame.repository

import de.tschuehly.weddingGame.model.CustomTheme
import de.tschuehly.weddingGame.model.Image
import de.tschuehly.weddingGame.model.WebsiteUser
import de.tschuehly.weddingGame.model.Wedding
import org.springframework.data.repository.CrudRepository

interface ImageRepository : CrudRepository<Image, Long>
interface WeddingRepository : CrudRepository<Wedding, Long>{
    fun findBySubdomain(subdomain: String): Wedding?
}
interface WebsiteUserRepository : CrudRepository<WebsiteUser, Long>{
    fun findByGoogleId(googleId: String) : WebsiteUser?
    fun findByFacebookId(facebookId: String): WebsiteUser?
}
interface CustomThemeRepository: CrudRepository<CustomTheme, Long>
