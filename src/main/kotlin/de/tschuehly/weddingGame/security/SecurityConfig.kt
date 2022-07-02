package de.tschuehly.weddingGame.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity(debug = false)
class SecurityConfig(
    val customOAuth2UserService: CustomOAuth2UserService,
    val customOidcUserService: CustomOidcUserService
    ){
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .headers().frameOptions().sameOrigin().and()
            .authorizeRequests()
            .antMatchers("/api/image/getUploadUrl").permitAll()
            .anyRequest().permitAll().and()
            .oauth2Login()
            .userInfoEndpoint()
            .userService(customOAuth2UserService)
            .oidcUserService (customOidcUserService)
        return http.csrf().disable().build()
    }
}
