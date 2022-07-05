package de.tschuehly.weddingGame.controller

import de.tschuehly.weddingGame.dto.ImageDTO
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
        @RequestParam("uuid") uuid: UUID?,
        @RequestParam("bucketId") bucketId: String,
        @RequestParam("fullName", required = false) fullName: String?,
        @RequestParam("fileName") fileName: String,
        request: HttpServletRequest
    ): ImageDTO {

        return imageService.getUploadUrl(uuid, fileName, bucketId)
    }

    @PostMapping("/create")
    fun create(
        @RequestBody imageDTO: ImageDTO
    ){
        imageService.save(imageDTO)
    }
}
