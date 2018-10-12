package mateuszmacholl.formica.specification

import mateuszmacholl.formica.model.notification.Notification
import net.kaczmarzyk.spring.data.jpa.domain.Equal
import net.kaczmarzyk.spring.data.jpa.domain.NotNull
import net.kaczmarzyk.spring.data.jpa.web.annotation.And
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec
import org.springframework.data.jpa.domain.Specification


@And(
        Spec(path = "notifiedUser.username", spec = Equal::class),
        Spec(path = "seen", params = ["hasBeenSeen"], spec = NotNull::class),
        Spec(path = "creationDate", spec = Equal::class)
)
interface NotificationSpec : Specification<Notification>