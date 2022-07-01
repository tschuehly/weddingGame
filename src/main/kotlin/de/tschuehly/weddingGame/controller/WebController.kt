package de.tschuehly.weddingGame.controller

import bo.Task
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Image
import com.itextpdf.text.Phrase
import com.itextpdf.text.RectangleReadOnly
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.ColumnText
import com.itextpdf.text.pdf.PdfWriter
import org.springframework.core.io.ClassPathResource
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.ModelAndView
import java.io.FileOutputStream
import java.util.*

@Controller
class WebController {
    val tasks: List<Task> = jacksonObjectMapper().readValue(ClassPathResource("data.json").inputStream)

    @GetMapping("/")
    fun index(
        @AuthenticationPrincipal principal: OAuth2User,
        model: Model
    ): String {
        model.addAllAttributes(
            mapOf(
                "email" to principal.attributes["email"],
                "userId" to principal.name
            )
        )
        return "index"
    }
    @GetMapping("/aufgabe/{uuid}")
    fun task(@PathVariable uuid: UUID, model: Model): String {
        tasks.find {
            it.uuid.toString() == uuid.toString()
        }?.let {
            model.addAttribute("task", it)
            return "task"
        } ?: let {
            return "404"
        }
    }

    @GetMapping("/qrcode")
    fun qrcode(): ModelAndView {
        val document = Document(RectangleReadOnly(369F, 255F))
        val pdf = PdfWriter.getInstance(document, FileOutputStream("pdf/test.pdf"))
        tasks.forEach { task ->
            document.newPage()
            document.open()
            val dancingScript = BaseFont.createFont("pdf/DancingScript-Bold.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED)
            val cb = pdf.directContent
            cb.beginText()
            cb.moveText(140F, 220F)
            cb.setColorFill(BaseColor(179, 96, 107))
            cb.setFontAndSize(dancingScript, 22F)
            cb.showText("Fotospiel fürs Gästebuch")
            cb.endText()

            val saira = BaseFont.createFont("pdf/SairaCondensed-Medium.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED)

            cb.setColorFill(BaseColor(179, 96, 107))
            cb.setFontAndSize(saira, 14F)
            val ct = ColumnText(cb)
            val myText = Phrase(task.title)
            ct.setSimpleColumn(myText, 120F, 0F, 270F, 90F, 15F, Element.ALIGN_LEFT)
            ct.go()
            cb.setColorFill(BaseColor(0, 0, 0))
            cb.setFontAndSize(saira, 10F)
            val ct2 = ColumnText(cb)
            ct2.setSimpleColumn(
                Phrase(
                    "Fotoaufgabe lesen\n" +
                        "Motiv suchen\n" +
                        "Fotografieren\n" +
                        "QR-Code scannen\n" +
                        "Foto hochladen\n"
                ),
                200F, 120F, 300F, 200F, 15F, Element.ALIGN_CENTER
            )
            ct2.go()

            val imgByte = RestTemplate().getForObject(
                "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=" +
                    "https://hochzeit.schuehly-it.de/aufgabe/${task.uuid}",
                ByteArray::class.java
            )
            document.add(
                Image.getInstance(imgByte).also {
                    it.setAbsolutePosition(220F, 120F)
                    it.setAbsolutePosition(290F, 10F)
                    it.scalePercent(48F)
                }
            )

            document.add(
                Image.getInstance("pdf/Blumen_grad.png").also {
                    it.setAbsolutePosition(-20F, -5F)
                    it.scalePercent(48F)
                }
            )
//            document.add(Image.getInstance("pdf/banners-1640595-svg.png").also {
//                it.setAbsolutePosition(120F,180F)
//                it.scalePercent(30F)
//            })
        }
        document.close()
        println("Done")
        return ModelAndView("index")
    }
}
