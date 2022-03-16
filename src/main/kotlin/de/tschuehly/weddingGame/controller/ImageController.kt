package de.tschuehly.weddingGame.controller

import com.itextpdf.text.Chunk
import com.itextpdf.text.Document
import com.itextpdf.text.FontFactory
import com.itextpdf.text.PageSize
import com.itextpdf.text.pdf.PdfWriter
import de.tschuehly.weddingGame.service.ImageService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("api/image")
class ImageController(
    val imageService: ImageService
) {

    @PostMapping("/upload/{uuid}", consumes = ["multipart/form-data"])
    fun uploadImages(
        @PathVariable uuid: UUID,
        @RequestParam("fullName") fullName: String,
        @RequestParam("images", required = false) images: Array<MultipartFile>?,
        request:HttpServletRequest
    ): String{
        return imageService.uploadImages(uuid, fullName, images)
    }


}
