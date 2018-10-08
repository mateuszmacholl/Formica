package mateuszmacholl.formica.dto.user

import mateuszmacholl.formica.validation.filled.Filled
import mateuszmacholl.formica.validation.user.uniqueEmail.UniqueEmail
import mateuszmacholl.formica.validation.user.uniqueUsername.UniqueUsername

data class CreateUserDto(
    @field:UniqueUsername
    @field:Filled
    val username: String,

    @field:UniqueEmail
    @field:Filled
    val email: String,

    @field:Filled
    val password: String,

    @field:Filled
    val url: String
)
