package de.tschuehly.weddingGame.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Wedding(
    @Id
    @GeneratedValue
    var id: Long,
    var subdomain: String,
    @OneToMany()
    var pictures: MutableList<Image> = ArrayList()
)
