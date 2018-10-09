package mateuszmacholl.formica.model.token

import mateuszmacholl.formica.model.user.User
import javax.persistence.Entity

@Entity
class VerificationToken(token: String? = null, user: User? = null) : Token(token, user)