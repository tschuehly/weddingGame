package de.tschuehly.weddingGame.model

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import javax.persistence.*

@Entity
class WebsiteUser(
    var googleId: String?,
    var facebookId: String?,
    var userEmail: String,
    var attributes: String?,
    @ElementCollection(fetch = FetchType.EAGER)
    var authorities: MutableSet<String> = mutableSetOf(),
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @OneToMany(fetch = FetchType.EAGER)
    var weddings: List<Wedding> = arrayListOf()
) : OidcUser{

    override fun getName(): String? {
       if (googleId != null){
           return googleId
       }
        return facebookId
    }

    override fun getAttributes(): MutableMap<String, Any> {
        attributes?.let {
            return jacksonObjectMapper().readValue(it)
        }?:let {
            return mutableMapOf()
        }
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
            return AuthorityUtils.createAuthorityList(*authorities.toTypedArray())

    }

    override fun getClaims(): MutableMap<String, Any>? {
        return null
    }

    override fun getUserInfo(): OidcUserInfo? {
        return null
    }

    override fun getIdToken(): OidcIdToken? {
        return null
    }


    override fun toString(): String {
        return "WebsiteUser(googleId=$googleId, facebookId=$facebookId, email='$userEmail', attributes=$attributes, authorities=$authorities, id=$id, weddings=$weddings)"
    }
}
