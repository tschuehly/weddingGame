package de.tschuehly.weddingGame.model

import java.util.*
import javax.persistence.*

@Entity
class Wedding(
    @Id
    @GeneratedValue
    var id: Long? = null,
    var subfolderId: String = UUID.randomUUID().toString(),
    var subdomain: String = UUID.randomUUID().toString(),
    @OneToMany(fetch = FetchType.EAGER)// TODO: Avoid Eager Fetching?
    var pictures: MutableList<Image> = arrayListOf()
){
    override fun toString(): String {
        return "Wedding(id=$id, subfolderId='$subfolderId', subdomain='$subdomain', pictures=$pictures)"
    }
}
