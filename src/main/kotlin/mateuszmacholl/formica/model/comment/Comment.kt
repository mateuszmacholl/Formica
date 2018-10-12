package mateuszmacholl.formica.model.comment

import mateuszmacholl.formica.model.answer.Answer
import mateuszmacholl.formica.model.user.User
import org.springframework.format.annotation.DateTimeFormat
import java.util.*
import javax.persistence.*

@Entity
data class Comment(
        var content: String? = null,
        @ManyToOne(fetch = FetchType.EAGER, targetEntity = Answer::class)
        @JoinColumn(name = "answer_id")
        var answer: Answer? = null,
        @ManyToOne(fetch = FetchType.EAGER, targetEntity = User::class)
        @JoinColumn(name = "author_id")
        var author: User? = null
) {
    @Id
    @GeneratedValue
    var id: Int? = null
    @DateTimeFormat
    var creationDate: Calendar = Calendar.getInstance()
}