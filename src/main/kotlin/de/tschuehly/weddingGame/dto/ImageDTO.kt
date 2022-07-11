package de.tschuehly.weddingGame.dto

data class ImageDTO(
    val uploadUrl: String? = null,
    val objectName: String,
    val fullName: String? = null,
    val fileName: String? = null,
)
