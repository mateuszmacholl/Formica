package mateuszmacholl.formica.specification

import mateuszmacholl.formica.model.user.User
import net.kaczmarzyk.spring.data.jpa.domain.Equal
import net.kaczmarzyk.spring.data.jpa.domain.Like
import net.kaczmarzyk.spring.data.jpa.domain.NotNull
import net.kaczmarzyk.spring.data.jpa.web.annotation.And
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec
import org.springframework.data.jpa.domain.Specification


@And(
        Spec(path = "username", spec = Like::class),
        Spec(path = "email", spec = Like::class),
        Spec(path = "roles", spec = Equal::class),
        Spec(path = "enabled", params = ["isActivated"], spec = NotNull::class),
        Spec(path = "creationDate", spec = Equal::class)
)
interface UserSpec : Specification<User>