package mateuszmacholl.formica.controller

import mateuszmacholl.formica.converter.post.PostConverter
import mateuszmacholl.formica.dto.answer.UpdateContentAnswerDto
import mateuszmacholl.formica.dto.post.CreatePostDto
import mateuszmacholl.formica.dto.post.UpdateBestAnswerPostDto
import mateuszmacholl.formica.dto.post.UpdateTagsPostDto
import mateuszmacholl.formica.dto.post.UpdateTitlePostDto
import mateuszmacholl.formica.service.post.PostService
import mateuszmacholl.formica.specification.PostSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
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
    @PreAuthorize("@JwtService.hasRole('user')")
    fun deleteById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val post = postService.findById(id)
        return if (!post.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            postService.delete(post.get())
            ResponseEntity<Any>(HttpStatus.NO_CONTENT )
        }
    }

    @RequestMapping(value = ["/{id}/answers"], method = [RequestMethod.GET])
    fun getComments(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val post = postService.findById(id)
        return if (!post.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            val answers = post.get().answers
            ResponseEntity<Any>(answers, HttpStatus.OK )
        }
    }

    @RequestMapping(value = ["/{id}/best-answer"], method = [RequestMethod.PATCH], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun setBestAnswer(@PathVariable(value = "id") id: Int,
                      @RequestBody @Validated updateBestAnswerPostDto: UpdateBestAnswerPostDto): ResponseEntity<*> {
        val post = postService.findById(id)
        return if (!post.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            val updatedPost = post.get()
            updatedPost.bestAnswer = updateBestAnswerPostDto.bestAnswer
            postService.add(updatedPost)
            ResponseEntity<Any>(updatedPost, HttpStatus.OK )
        }
    }

    @RequestMapping(value = ["/{id}/tags"], method = [RequestMethod.PATCH], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun setTags(@PathVariable(value = "id") id: Int,
                      @RequestBody @Validated updateTagsPostDto: UpdateTagsPostDto): ResponseEntity<*> {
        val post = postService.findById(id)
        return if (!post.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            val updatedPost = post.get()
            updatedPost.tags = postConverter.toEntity(updateTagsPostDto)
            postService.add(updatedPost)
            ResponseEntity<Any>(updatedPost, HttpStatus.OK )
        }
    }

    @RequestMapping(value = ["/{id}/title"], method = [RequestMethod.PATCH], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun setTitle(@PathVariable(value = "id") id: Int,
                @RequestBody @Validated updateTitlePostDto: UpdateTitlePostDto): ResponseEntity<*> {
        val post = postService.findById(id)
        return if (!post.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            val updatedPost = post.get()
            updatedPost.title = updateTitlePostDto.title
            postService.add(updatedPost)
            ResponseEntity<Any>(updatedPost, HttpStatus.OK )
        }
    }

    @RequestMapping(value = ["/{id}/content"], method = [RequestMethod.PATCH], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun setContent(@PathVariable(value = "id") id: Int,
                           @RequestBody @Validated updateContentAnswerDto: UpdateContentAnswerDto): ResponseEntity<*> {
        val answer = postService.findById(id)
        return if (!answer.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            val updatedAnswer = answer.get()
            updatedAnswer.content = updateContentAnswerDto.content
            postService.add(updatedAnswer)
            ResponseEntity<Any>(updatedAnswer, HttpStatus.OK )
        }
    }

/*
    @RequestMapping(value = ["/{id}/best-answer"], method = [RequestMethod.GET])
    fun getBestAnswer(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val post = postService.findById(id)
        return if (!post.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            val bestAnswer = post.get().bestAnswer
            ResponseEntity<Any>(bestAnswer, HttpStatus.OK )
        }
    }
    */
}