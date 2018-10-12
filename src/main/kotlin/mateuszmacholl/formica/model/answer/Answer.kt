package mateuszmacholl.formica.model.answer

import com.fasterxml.jackson.annotation.JsonIgnore
import mateuszmacholl.formica.model.comment.Comment
import mateuszmacholl.formica.model.post.Post
import mateuszmacholl.formica.model.user.User
import org.springframework.format.annotation.DateTimeFormat
import java.util.*
import javax.persistence.*

@Entity
data class Answer(
        var content: String? = null,
        @ManyToOne(fetch = FetchType.EAGER, targetEntity = Post::class)
        @JoinColumn(name = "post_id")
        var post: Post? = null,
        @ManyToOne(fetch = FetchType.EAGER, targetEntity = User::class)
        @JoinColumn(name = "author_id")
        var author: User? = null
) {
    @Id
    @GeneratedValue
    var id: Int? = null
    @DateTimeFormat
    var creationDate: Calendar = Calendar.getInstance()
    var votes: Int = 0
    @JsonIgnore
    @OneToMany(mappedBy = "answer", cascade = [CascadeType.ALL], orphanRemoval = true, targetEntity = Comment::class)
    var comments: MutableSet<Comment> = mutableSetOf()
    var best: Boolean = false
}