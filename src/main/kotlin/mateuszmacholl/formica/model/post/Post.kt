package mateuszmacholl.formica.model.post

import com.fasterxml.jackson.annotation.JsonIgnore
import mateuszmacholl.formica.model.answer.Answer
import mateuszmacholl.formica.model.coordinates.Coordinates
import mateuszmacholl.formica.model.tag.Tag
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
        var author: User? = null,
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name="coordinates_id")
        var coordinates: Coordinates? = null
){
    @Id
    @GeneratedValue
    var id: Int? = null
    @DateTimeFormat
    var creationDate: Calendar = Calendar.getInstance()
    @ManyToMany(targetEntity = Tag::class)
    @JoinTable(name = "post_tag",
            inverseJoinColumns = [JoinColumn(name = "post_id", referencedColumnName = "id")],
            joinColumns = [JoinColumn(name = "tag_id", referencedColumnName = "id")])
    var tags: Set<Tag> = mutableSetOf()
    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true, targetEntity = Answer::class)
    var answers: Set<Answer> = mutableSetOf()
    var votes: Int = 0
}