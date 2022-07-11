package de.tschuehly.weddingGame.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
class CustomTheme(
    @Id
    @GeneratedValue
    var id: Long? = null,
    var primaryColor: String?,
    var secondaryColor: String?,
    var base100Color: String?,
    var base300Color: String?,
    var textColor: String?,
    var primaryTextColor: String?,
    var secondaryTextColor: String?,
    var buttonRadius: String?,
    var fontFamily: String?,
    var backgroundGradient: String?,
    @OneToOne
    var coverImage: Image?
)
