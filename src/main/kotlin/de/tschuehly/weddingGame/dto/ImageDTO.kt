package de.tschuehly.weddingGame.dto

data class ImageDTO(
    val objectName: String,
    val folderId: String,
    val uploadUrl: String? = null
)
