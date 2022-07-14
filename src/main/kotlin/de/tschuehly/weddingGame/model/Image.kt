package de.tschuehly.weddingGame.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
class Image(
    @Id
    @GeneratedValue
    var id: Long? = null,
    var objectName: String,
    var downloadUrl: String?,
    var downloadUrlExpiry: Date?,
    @OneToOne
    var thumbnail: Image? = null
) {
    override fun toString(): String {
        return "Image(id=$id, objectName='$objectName', downloadUrl=$downloadUrl, downloadUrlExpiry=$downloadUrlExpiry)"
    }
}
