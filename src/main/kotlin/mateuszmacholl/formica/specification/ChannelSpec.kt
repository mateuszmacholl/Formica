package mateuszmacholl.formica.specification

import mateuszmacholl.formica.model.channel.Channel
import net.kaczmarzyk.spring.data.jpa.domain.Like
import net.kaczmarzyk.spring.data.jpa.web.annotation.And
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec
import org.springframework.data.jpa.domain.Specification

@And(
        Spec(path = "name", spec = Like::class)
)
interface ChannelSpec: Specification<Channel>
