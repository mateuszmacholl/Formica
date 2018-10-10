package mateuszmacholl.formica.controller.user

import mateuszmacholl.formica.converter.comment.CommentConverter
import mateuszmacholl.formica.dto.comment.CreateCommentDto
import mateuszmacholl.formica.service.comment.CommentService
import mateuszmacholl.formica.specification.CommentSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping(value =  ["/comments"])
class CommentController {
    @Autowired
    lateinit var commentService: CommentService
    @Autowired
    lateinit var commentConverter: CommentConverter

    @RequestMapping(value = [""], method = [RequestMethod.GET])
    fun getAllBy(commentSpec: CommentSpec, pageable: Pageable): ResponseEntity<*> {
        val comments = commentService.findAll(commentSpec, pageable)
        return ResponseEntity(comments, HttpStatus.OK)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val comment = commentService.findById(id)
        return if (!comment.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity(comment.get(), HttpStatus.OK)
        }
    }

    @RequestMapping(value = [""], method = [RequestMethod.POST])
    fun add(@RequestBody @Validated createCommentDto: CreateCommentDto): ResponseEntity<*> {
        val comment = commentConverter.toEntity(createCommentDto)
        commentService.add(comment)
        return ResponseEntity<Any>(HttpStatus.CREATED)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    fun deleteById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val comment = commentService.findById(id)
        return if (!comment.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            commentService.delete(comment.get())
            ResponseEntity<Any>(HttpStatus.NO_CONTENT )
        }
    }
}