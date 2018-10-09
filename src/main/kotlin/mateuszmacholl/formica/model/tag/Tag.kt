package mateuszmacholl.formica.model.tag

import com.fasterxml.jackson.annotation.JsonIgnore
import mateuszmacholl.formica.model.post.Post
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToMany

@Entity
data class Tag(
        var name: String? = null
) {
    @Id
    @GeneratedValue
    var id: Int? = null
    @ManyToMany(mappedBy = "tags", targetEntity = Post::class)
    @JsonIgnore
    var posts: Set<Post> = mutableSetOf()
}