package mateuszmacholl.formica.model.notification

import mateuszmacholl.formica.model.post.Post
import mateuszmacholl.formica.model.user.User
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class BestAnswerHasBeenChosenNotification(
        notifiedUser: User? = null,
        notifierUser: User? = null,
        type: String = "best_answer_has_been_chosen",
        @ManyToOne(fetch = FetchType.EAGER, targetEntity = Post::class)
        @OnDelete(action = OnDeleteAction.CASCADE)
        @JoinColumn(name = "post_id")
        var post: Post? = null
) : Notification(notifiedUser, notifierUser, type)