package mateuszmacholl.formica.controller

import mateuszmacholl.formica.converter.post.PostConverter
import mateuszmacholl.formica.dto.post.CreatePostDto
import mateuszmacholl.formica.service.post.PostService
import mateuszmacholl.formica.specification.PostSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping(value =  ["/posts"])
class PostController {
    @Autowired
    lateinit var postService: PostService
    @Autowired
    lateinit var postConverter: PostConverter

    @RequestMapping(value = [""], method = [RequestMethod.GET])
    fun getAllBy(postSpec: PostSpec, pageable: Pageable): ResponseEntity<*> {
        val posts = postService.findAll(postSpec, pageable)
        return ResponseEntity(posts, HttpStatus.OK)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val post = postService.findById(id)
        return if (!post.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity(post.get(), HttpStatus.OK)
        }
    }

    @RequestMapping(value = [""], method = [RequestMethod.POST])
    fun add(@RequestBody @Validated createPostDto: CreatePostDto): ResponseEntity<*> {
        val post = postConverter.toEntity(createPostDto)
        postService.add(post)
        return ResponseEntity<Any>(HttpStatus.CREATED)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    fun deleteById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val post = postService.findById(id)
        return if (!post.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            postService.delete(post.get())
            ResponseEntity<Any>(HttpStatus.NO_CONTENT )
        }
    }

    @RequestMapping(value = ["/{id}/comments"], method = [RequestMethod.GET])
    fun getComments(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val post = postService.findById(id)
        return if (!post.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            val comments = post.get().comments
            ResponseEntity<Any>(comments, HttpStatus.OK )
        }
    }
}