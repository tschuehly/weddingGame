package de.tschuehly.weddingGame.controller

import de.tschuehly.weddingGame.service.ImageService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("api/image")
class ImageController(
    val imageService: ImageService
) {

    @PostMapping("/upload/{id}", consumes = ["multipart/form-data"])
    fun uploadImages(
        @PathVariable id: Long,
        @RequestParam("fullName") fullName: String,
        @RequestParam("images", required = false) images: Array<MultipartFile>?,
        request:HttpServletRequest
    ): String{
        return imageService.uploadImages(id, fullName, images)
    }

}
