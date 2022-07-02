package de.tschuehly.weddingGame.service

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.tschuehly.weddingGame.model.WebsiteUser
import de.tschuehly.weddingGame.model.Wedding
import de.tschuehly.weddingGame.repository.WebsiteUserRepository
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class WebsiteUserService(
    val userRepository: WebsiteUserRepository,
    val weddingService: WeddingService
) {
    fun createUserIfNotExists(user: OAuth2User): WebsiteUser {
        val email: String = user.attributes["email"] as String
        val userId = user.name
        val weddingList = arrayListOf(weddingService.save(Wedding()))
        if (user.attributes["iss"].toString() == "https://accounts.google.com") {
            return userRepository.findByGoogleId(user.name) ?:
                userRepository.save(WebsiteUser(userId,null, email,
                    weddings = weddingList,
                    attributes = jacksonObjectMapper().registerModule(JavaTimeModule())
                        .writeValueAsString(user.attributes) ,
                    authorities = AuthorityUtils.authorityListToSet(user.authorities).also { it.add("ROLE_ADMIN") }
                ))
        }
        return userRepository.findByFacebookId(user.name) ?:
            userRepository.save(WebsiteUser(null,userId,email,
                weddings = weddingList,
                attributes = jacksonObjectMapper().writeValueAsString(user.attributes) ,
                authorities = AuthorityUtils.authorityListToSet(user.authorities)))
    }
}
