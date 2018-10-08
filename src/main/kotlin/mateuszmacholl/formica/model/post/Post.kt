package mateuszmacholl.formica.model.post

import mateuszmacholl.formica.model.post.tag.Tag
import mateuszmacholl.formica.model.user.User
import org.springframework.format.annotation.DateTimeFormat
import java.util.*
import javax.persistence.*

@Entity
data class Post(
        var title: String? = null,
        var content: String? = null,
        @ManyToOne(fetch = FetchType.EAGER, targetEntity = User::class)
        @JoinColumn(name = "author_id")
        var author: User? = null
){
    @Id
    @GeneratedValue
    var id: Int? = null
    @DateTimeFormat
    var creationDate: Calendar = Calendar.getInstance()
    @ManyToMany(targetEntity = Tag::class)
    @JoinTable(name = "post_tag",
            joinColumns = [JoinColumn(name = "post_id", referencedColumnName = "id")],
            inverseJoinColumns = [JoinColumn(name = "tag_id", referencedColumnName = "id")])
    var tags: Set<Tag> = mutableSetOf()
    var solved: Boolean = false
    var votes: Int = 0

}