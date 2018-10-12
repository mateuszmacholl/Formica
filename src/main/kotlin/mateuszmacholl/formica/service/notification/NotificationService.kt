package mateuszmacholl.formica.service.notification

import mateuszmacholl.formica.model.notification.Notification
import mateuszmacholl.formica.repo.NotificationRepo
import mateuszmacholl.formica.specification.NotificationSpec
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class NotificationService
constructor(private val notificationRepo: NotificationRepo) {
    fun findById(id: Int): Optional<Notification> {
        return notificationRepo.findById(id)
    }

    fun findAll(notificationSpec: NotificationSpec, pageable: Pageable): MutableIterable<Notification> {
        return notificationRepo.findAll(notificationSpec, pageable)
    }

    fun add(notification: Notification) {
        notificationRepo.save(notification)
    }

    fun delete(notification: Notification) {
        notificationRepo.delete(notification)
    }
}