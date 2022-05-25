package de.tschuehly.weddingGame.controller

import de.tschuehly.weddingGame.service.ImageService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("api/image")
class ImageController(
    val imageService: ImageService
) {
    @PostMapping("/upload", consumes = ["multipart/form-data"])
    fun uploadImages(
        @RequestParam("fullName", required = false) fullName: String?,
        @RequestParam("images", required = false) images: Array<MultipartFile>?,
        request:HttpServletRequest
    ): String{
        return imageService.uploadImages("safari",fullName, images)
    }

    @PostMapping("/upload/{uuid}", consumes = ["multipart/form-data"])
    fun uploadTaskImages(
        @PathVariable uuid: UUID,
        @RequestParam("fullName") fullName: String,
        @RequestParam("images", required = false) images: Array<MultipartFile>?,
        request:HttpServletRequest
    ): String{
        return imageService.uploadImages(uuid.toString(), fullName, images)
    }


}
