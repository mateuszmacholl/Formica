package mateuszmacholl.formica.model.notification

import mateuszmacholl.formica.model.user.User
import javax.persistence.Entity

@Entity
class PostHasBeenCommentedNotification(
        notifiedUser: User? = null,
        type: String = "post_has_been_commented"
) : Notification(notifiedUser, type)