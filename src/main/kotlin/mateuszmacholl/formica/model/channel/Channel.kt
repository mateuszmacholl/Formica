package mateuszmacholl.formica.model.channel

import com.fasterxml.jackson.annotation.JsonIgnore
import mateuszmacholl.formica.model.coordinates.Coordinates
import mateuszmacholl.formica.model.post.Post
import javax.persistence.*

@Entity
data class Channel(
  var name: String? = null,
  @OneToOne(cascade = [CascadeType.ALL])
  @JoinColumn(name = "coordinates_id")
  var coordinates: Coordinates? = null
) {
    @Id
    @GeneratedValue
    var id: Int? = null
    @JsonIgnore
    @OneToMany(mappedBy = "channel", cascade = [CascadeType.ALL], orphanRemoval = true, targetEntity = Post::class)
    var posts: Set<Post> = mutableSetOf()
}