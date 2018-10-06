package mateuszmacholl.formica.dto.user

import mateuszmacholl.formica.validation.filled.Filled
import mateuszmacholl.formica.validation.uniqueEmail.UniqueEmail
import mateuszmacholl.formica.validation.uniqueUsername.UniqueUsername

class CreateUserDto {
    @UniqueUsername
    @Filled
    var username: String? = null

    @UniqueEmail
    @Filled
    var email: String? = null

    @Filled
    var password: String? = null

    @Filled
    var url: String? = null
}
