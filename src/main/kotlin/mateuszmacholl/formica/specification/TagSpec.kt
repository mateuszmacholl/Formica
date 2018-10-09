package mateuszmacholl.formica.specification

import mateuszmacholl.formica.model.tag.Tag
import net.kaczmarzyk.spring.data.jpa.domain.Like
import net.kaczmarzyk.spring.data.jpa.web.annotation.And
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec
import org.springframework.data.jpa.domain.Specification


@And(
        Spec(path = "name", spec = Like::class)
)
interface TagSpec: Specification<Tag>