package de.tschuehly.weddingGame.controller

import de.tschuehly.weddingGame.service.ImageService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("api/image")
class ImageController(
    val imageService: ImageService
) {
    @GetMapping("/getUploadUrl")
    fun uploadImages(
        @RequestParam("uuid") uuid: UUID?,
        @RequestParam("fullName", required = false) fullName: String?,
        @RequestParam("fileName") fileName: String,
        request: HttpServletRequest
    ): String {
        return imageService.getUploadPresignedObjectUrl(uuid, fullName, fileName)
    }
}
