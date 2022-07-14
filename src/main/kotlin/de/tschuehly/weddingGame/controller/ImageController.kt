package de.tschuehly.weddingGame.controller

import de.tschuehly.weddingGame.dto.ImageDTO
import de.tschuehly.weddingGame.model.Image
import de.tschuehly.weddingGame.service.ImageService
import de.tschuehly.weddingGame.service.WeddingService
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest


@RestController
@RequestMapping("api/image")
class ImageController(
    val imageService: ImageService,
    val weddingService: WeddingService
) {
    @GetMapping("/getUploadUrl")
    fun uploadImage(
        @RequestParam("folderId") folderId: String,
        @RequestParam("subfolderId") subfolderId: String?,
        @RequestParam("fileName") fileName: String,
        request: HttpServletRequest
    ): ImageDTO {
        return imageService.getUploadUrl(folderId, subfolderId ,fileName)
    }

    @PostMapping("/create")
    fun create(
        @RequestBody imageDTO: ImageDTO
    ): Image {
        return weddingService.saveImage(imageDTO)
    }
}
