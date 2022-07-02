package de.tschuehly.weddingGame.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .headers().frameOptions().sameOrigin().and()
            .authorizeRequests()
            .antMatchers("/api/image/getUploadUrl").permitAll()
            .anyRequest().permitAll()
            .and()
            .oauth2Login()
        return http.csrf().disable().build()
    }
}
