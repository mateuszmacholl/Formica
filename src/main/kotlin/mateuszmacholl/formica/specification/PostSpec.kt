package mateuszmacholl.formica.specification

import mateuszmacholl.formica.model.post.Post
import net.kaczmarzyk.spring.data.jpa.domain.Equal
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThan
import net.kaczmarzyk.spring.data.jpa.domain.Like
import net.kaczmarzyk.spring.data.jpa.domain.NotNull
import net.kaczmarzyk.spring.data.jpa.web.annotation.And
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec
import org.springframework.data.jpa.domain.Specification

@And(
        Spec(path = "title", spec = Like::class),
        Spec(path = "author.username", spec = Like::class),
        Spec(path = "roles", spec = Equal::class),
        Spec(path = "tags", spec = Equal::class),
        Spec(path = "votes", spec = GreaterThan::class),
        Spec(path = "solved", params = ["isSolved"], spec = NotNull::class),
        Spec(path = "creationDate", spec = Equal::class)
)
interface PostSpec : Specification<Post>