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
    @PutMapping("/saveCustomTheme", consumes = ["multipart/form-data"])
    fun saveCustomTheme(
        @RequestParam("weddingId") weddingId: Long,
        @RequestParam customThemeString: String,
        @RequestParam coverImageFile: MultipartFile
    ){
        weddingService.setCustomTheme(weddingId, customThemeString, coverImageFile)
    }
}
