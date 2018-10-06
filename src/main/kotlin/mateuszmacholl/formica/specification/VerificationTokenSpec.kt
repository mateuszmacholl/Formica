package mateuszmacholl.formica.specification

import mateuszmacholl.formica.model.user.VerificationToken
import net.kaczmarzyk.spring.data.jpa.domain.Equal
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThan
import net.kaczmarzyk.spring.data.jpa.domain.Like
import net.kaczmarzyk.spring.data.jpa.web.annotation.And
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec
import org.springframework.data.jpa.domain.Specification

@And(
        Spec(path = "token", spec = Equal::class),
        Spec(path = "expirationDate", params = ["expireAfter"], spec = GreaterThan::class),
        Spec(path = "user.username", params = ["username"], spec = Like::class),
        Spec(path = "user.email", params = ["email"], spec = Like::class)
)
interface VerificationTokenSpec : Specification<VerificationToken>