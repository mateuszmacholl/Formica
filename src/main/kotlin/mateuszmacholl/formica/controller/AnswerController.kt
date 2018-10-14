package mateuszmacholl.formica.controller

import mateuszmacholl.formica.converter.answer.AnswerConverter
import mateuszmacholl.formica.dto.answer.CreateAnswerDto
import mateuszmacholl.formica.service.answer.AnswerService
import mateuszmacholl.formica.specification.AnswerSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping(value =  ["/answers"])
class AnswerController {
    @Autowired
    lateinit var answerService: AnswerService
    @Autowired
    lateinit var answerConverter: AnswerConverter

    @RequestMapping(value = [""], method = [RequestMethod.GET])
    fun getAllBy(answerSpec: AnswerSpec, pageable: Pageable): ResponseEntity<*> {
        val answers = answerService.findAll(answerSpec, pageable)
        return ResponseEntity(answers, HttpStatus.OK)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val answer = answerService.findById(id)
        return if (!answer.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity(answer.get(), HttpStatus.OK)
        }
    }

    @RequestMapping(value = [""], method = [RequestMethod.POST])
    fun add(@RequestBody @Validated createAnswerDto: CreateAnswerDto): ResponseEntity<*> {
        val answer = answerConverter.toEntity(createAnswerDto)
        answerService.add(answer)
        return ResponseEntity<Any>(HttpStatus.CREATED)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    fun deleteById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val answer = answerService.findById(id)
        return if (!answer.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            answerService.delete(answer.get())
            ResponseEntity<Any>(HttpStatus.NO_CONTENT )
        }
    }

    @RequestMapping(value = ["/{id}/comments"], method = [RequestMethod.GET])
    fun getComments(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val answer = answerService.findById(id)
        return if (!answer.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            val comments = answer.get().comments
            ResponseEntity<Any>(comments, HttpStatus.OK )
        }
    }

    @RequestMapping(value = ["/{id}/posts"], method = [RequestMethod.GET])
    fun getBestAnswer(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val answer = answerService.findById(id)
        return if (!answer.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            val post = answer.get().post
            ResponseEntity<Any>(post, HttpStatus.OK )
        }
    }
}