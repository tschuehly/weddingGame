package de.tschuehly.weddingGame.controller

import bo.Task
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfWriter
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.ModelAndView
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.util.*
import javax.servlet.http.HttpServletResponse

@Controller
class WebController {
    val tasks: List<Task> = jacksonObjectMapper().readValue(ClassPathResource("data.json").file)
    @GetMapping("/")
    fun index(model: MutableMap<String, List<Task>>): ModelAndView{
        model["tasks"] = tasks
        return ModelAndView("index",model)

    }
    @GetMapping("/aufgabe/{uuid}")
    fun task(@PathVariable uuid: UUID, model: MutableMap<String, Task>): ModelAndView {
        tasks.find {
            it.uuid.toString() == uuid.toString()
        }?.let {
            model["task"] = it
            return ModelAndView("task",model)
        }?:let {
            return ModelAndView("404")
        }
    }

    @GetMapping("/qrcode")
    fun qrcode(response: HttpServletResponse): ModelAndView {
        val document = Document(RectangleReadOnly(369F,255F))
        val pdf = PdfWriter.getInstance(document, FileOutputStream("pdf/test.pdf"))
        tasks.first { task ->
            document.newPage()
            document.open()
            val font = FontFactory.getFont(FontFactory.COURIER)
            val chunk = Chunk("Mache ein Foto " + task.title, font)
            document.add(chunk)

            val imgByte = RestTemplate().getForObject("https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=" +
                    "https://hochzeit.schuehly-it.de/aufgabe/${task.uuid}",
                ByteArray::class.java)
            document.add(Image.getInstance(imgByte).also { it.setAbsolutePosition(220F,120F) })

            document.add(Image.getInstance("pdf/Blumen.png").also {
                it.scalePercent(50F)
            })
        }
        document.close()
        return ModelAndView("index")
    }
}
