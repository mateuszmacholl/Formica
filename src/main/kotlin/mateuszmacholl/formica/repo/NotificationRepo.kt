package mateuszmacholl.formica.repo

import mateuszmacholl.formica.model.notification.Notification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository


@Repository
interface NotificationRepo: JpaRepository<Notification, Int>, JpaSpecificationExecutor<Notification>