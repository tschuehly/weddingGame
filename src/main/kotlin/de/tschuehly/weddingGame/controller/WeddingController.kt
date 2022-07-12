package de.tschuehly.weddingGame.controller

import de.tschuehly.weddingGame.service.WeddingService
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/wedding")
class WeddingController(
    val weddingService: WeddingService
){
    @PutMapping("/customTheme", consumes = ["multipart/form-data"])
    fun setCustomTheme(
        @RequestParam("weddingId") weddingId: Long,
        @RequestParam customThemeString: String,
        @RequestParam coverImageFile: MultipartFile?
    ) = weddingService.setCustomTheme(weddingId, customThemeString, coverImageFile)


    @PutMapping("/theme")
    fun setTheme(
        @RequestParam("weddingId") weddingId: Long,
        @RequestParam("theme") theme: String
    ) = weddingService.setTheme(weddingId,theme)
}
