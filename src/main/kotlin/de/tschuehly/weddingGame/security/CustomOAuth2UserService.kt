package de.tschuehly.weddingGame.security

import de.tschuehly.weddingGame.service.WebsiteUserService
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
    val websiteUserService: WebsiteUserService
) : OAuth2UserService<OAuth2UserRequest,OAuth2User>{
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val user = DefaultOAuth2UserService().loadUser(userRequest)
        return websiteUserService.createUserIfNotExists(user)
    }

}
