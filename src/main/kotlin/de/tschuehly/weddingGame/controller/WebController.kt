package de.tschuehly.weddingGame.controller

import bo.Task
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.servlet.ModelAndView

@Controller
class WebController {
    val tasks: List<Task> = jacksonObjectMapper().readValue(ClassPathResource("data.json").file)
    @GetMapping("/")
    fun index(model: MutableMap<String, List<Task>>): ModelAndView{
        model["tasks"] = tasks
        return ModelAndView("index",model)

    }
    @GetMapping("/aufgabe/{id}")
    fun task(@PathVariable id: Number, model: MutableMap<String, Task>): ModelAndView {
        tasks.find {
            it.id.toString() == id.toString()
        }?.let {
            model["task"] = it
            return ModelAndView("task",model)
        }?:let {
            return ModelAndView("404")
        }
    }
}
