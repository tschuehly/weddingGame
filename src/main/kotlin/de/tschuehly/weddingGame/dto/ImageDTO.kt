package de.tschuehly.weddingGame.dto

data class ImageDTO(
    val uploadUrl: String,
    val objectName: String,
    val fullName: String?,
    val fileName: String,
    val subfolderId: String
)
