package mateuszmacholl.formica.controller

import mateuszmacholl.formica.service.notification.NotificationService
import mateuszmacholl.formica.specification.NotificationSpec
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
@RequestMapping(value = ["/notifications"])
class NotificationController {
    @Autowired
    lateinit var notificationService: NotificationService

    @RequestMapping(value = [""], method = [RequestMethod.GET])
    fun getAllBy(notificationSpec: NotificationSpec, pageable: Pageable): ResponseEntity<*> {
        val notifications = notificationService.findAll(notificationSpec, pageable)
        return ResponseEntity(notifications, HttpStatus.OK)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val notification = notificationService.findById(id)
        return if (!notification.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity(notification.get(), HttpStatus.OK)
        }
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    fun deleteById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val notification = notificationService.findById(id)
        return if (!notification.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            notificationService.delete(notification.get())
            ResponseEntity<Any>(HttpStatus.NO_CONTENT)
        }
    }
}