package de.tschuehly.weddingGame.controller

import de.tschuehly.weddingGame.dto.ImageDTO
import de.tschuehly.weddingGame.model.Image
import de.tschuehly.weddingGame.service.ImageService
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("api/image")
class ImageController(
    val imageService: ImageService
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
        return imageService.save(imageDTO)
    }
}
