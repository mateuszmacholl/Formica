package mateuszmacholl.formica.controller.user

import mateuszmacholl.formica.converter.tag.TagConverter
import mateuszmacholl.formica.dto.tag.CreateTagDto
import mateuszmacholl.formica.service.tag.TagService
import mateuszmacholl.formica.specification.TagSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping(value = ["/tags"])
class TagController {
    @Autowired
    lateinit var tagService: TagService
    @Autowired
    lateinit var tagConverter: TagConverter

    @RequestMapping(value = [""], method = [RequestMethod.GET])
    fun getAllBy(tagSpec: TagSpec, pageable: Pageable): ResponseEntity<*> {
        val tags = tagService.findAll(tagSpec, pageable)
        return ResponseEntity(tags, HttpStatus.OK)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val tag = tagService.findById(id)
        return if (!tag.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity(tag.get(), HttpStatus.OK)
        }
    }

    @RequestMapping(value = [""], method = [RequestMethod.POST])
    fun add(@RequestBody @Validated createTagDto: CreateTagDto): ResponseEntity<*> {
        val tag = tagConverter.toEntity(createTagDto)
        tagService.add(tag)
        return ResponseEntity<Any>(HttpStatus.CREATED)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    fun deleteById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val tag = tagService.findById(id)
        return if (!tag.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            tagService.delete(tag.get())
            ResponseEntity<Any>(HttpStatus.NO_CONTENT )
        }
    }

    @RequestMapping(value = ["/{id}/posts"], method = [RequestMethod.GET])
    fun getPosts(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val tag = tagService.findById(id)
        return if (!tag.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            val posts = tag.get().posts
            ResponseEntity<Any>(posts, HttpStatus.OK )
        }
    }

}