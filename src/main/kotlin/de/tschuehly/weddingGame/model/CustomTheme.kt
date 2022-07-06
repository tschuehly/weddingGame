package de.tschuehly.weddingGame.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class CustomTheme(
    @Id
    @GeneratedValue
    var id: Long? = null,
    var primaryColor: String?,
    var secondaryColor: String?,
    var accentColor: String?,
    var neutralColor: String?,
    var baseColor: String?
)
