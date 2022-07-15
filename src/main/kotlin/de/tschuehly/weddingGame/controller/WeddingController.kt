package de.tschuehly.weddingGame.controller

import de.tschuehly.weddingGame.model.Image
import de.tschuehly.weddingGame.service.WeddingService
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Flux
import java.time.Duration
import javax.servlet.http.HttpServletRequest

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

    @GetMapping("/images")
    fun getAll(
        request: HttpServletRequest
    ): MutableIterable<Image> {
        val subdomain = request.serverName.split(".").first()
        return weddingService.getAllImagesBySubdomain(subdomain)
    }

    @GetMapping("/images-sse")
    fun getImagesAsSSE(
        request: HttpServletRequest
    ): Flux<ServerSentEvent<MutableList<Image>>> {
        val subdomain = request.serverName.split(".").first()
        return Flux.interval(Duration.ofSeconds(5))
            .map {
                ServerSentEvent.builder<MutableList<Image>>()
                    .event("periodic-event")
                    .data(weddingService.getBySubdomain(subdomain)?.pictures).build()
            }
    }
}
