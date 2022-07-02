package de.tschuehly.weddingGame.security

import de.tschuehly.weddingGame.service.WebsiteUserService
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Service

@Service
class CustomOidcUserService(
    private val websiteUserService: WebsiteUserService
): OidcUserService() {
    override fun loadUser(userRequest: OidcUserRequest?): OidcUser {
        val user = super.loadUser(userRequest)
        return websiteUserService.createUserIfNotExists(user)
    }
}
