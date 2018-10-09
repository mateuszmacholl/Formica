package mateuszmacholl.formica.dto.token.verificationToken

import mateuszmacholl.formica.validation.filled.Filled
import mateuszmacholl.formica.validation.user.existUserWithUsername.ExistAccountWithUsername
import javax.validation.constraints.NotNull

data class CreateVerificationTokenDto(
        @field:NotNull
        @field:ExistAccountWithUsername
        val user: String,
        @field:Filled
        val token: String
)