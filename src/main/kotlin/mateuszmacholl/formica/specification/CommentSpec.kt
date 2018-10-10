package mateuszmacholl.formica.specification

import mateuszmacholl.formica.model.comment.Comment
import net.kaczmarzyk.spring.data.jpa.domain.Equal
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThan
import net.kaczmarzyk.spring.data.jpa.domain.Like
import net.kaczmarzyk.spring.data.jpa.web.annotation.And
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec
import org.springframework.data.jpa.domain.Specification

@And(
        Spec(path = "author.username", spec = Like::class),
        Spec(path = "post.title", spec = Like::class),
        Spec(path = "votes", spec = GreaterThan::class),
        Spec(path = "creationDate", spec = Equal::class)
)
interface CommentSpec: Specification<Comment>