package mateuszmacholl.formica.controller.user

import mateuszmacholl.formica.service.user.UserService
import mateuszmacholl.formica.specification.UserSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping(value = ["/users"])
class UserController {
    @Autowired
    lateinit var userService: UserService

    @RequestMapping(value = [""], method = [RequestMethod.GET])
    fun getAllBy(userSpec: UserSpec, pageable: Pageable): ResponseEntity<*> {
        val users = userService.findAll(userSpec, pageable)
        return ResponseEntity(users, HttpStatus.OK)
    }

    @RequestMapping(value = ["/{id}"])
    fun getById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val user = userService.findById(id)
        return if (!user.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            return ResponseEntity(user, HttpStatus.OK)
        }
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    fun deleteById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val user = userService.findById(id)
        return if (!user.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            userService.delete(user.get())
            ResponseEntity<Any>(HttpStatus.NO_CONTENT)
        }
    }

    @RequestMapping(value = ["/{id}/posts"], method = [RequestMethod.GET])
    fun getPosts(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val user = userService.findById(id)
        return if (!user.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            val posts = user.get().posts
            ResponseEntity<Any>(posts, HttpStatus.OK )
        }
    }

    @RequestMapping(value = ["/{id}/comments"], method = [RequestMethod.GET])
    fun getComments(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val user = userService.findById(id)
        return if (!user.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            val comments = user.get().comments
            ResponseEntity<Any>(comments, HttpStatus.OK )
        }
    }

    @RequestMapping(value = ["/{id}/answers"], method = [RequestMethod.GET])
    fun getAnswers(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val user = userService.findById(id)
        return if (!user.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            val answers = user.get().answers
            ResponseEntity<Any>(answers, HttpStatus.OK )
        }
    }

    @RequestMapping(value = ["/{id}/notifications"], method = [RequestMethod.GET])
    fun getNotifications(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val user = userService.findById(id)
        return if (!user.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            val notifications = user.get().notifications
            ResponseEntity<Any>(notifications, HttpStatus.OK )
        }
    }

}