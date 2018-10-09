package mateuszmacholl.formica.dto.token.passwordResetToken

import mateuszmacholl.formica.validation.filled.Filled
import mateuszmacholl.formica.validation.user.existUserWithUsername.ExistAccountWithUsername
import javax.validation.constraints.NotNull

data class CreatePasswordResetTokenDto(
        @field:NotNull
        @field:ExistAccountWithUsername
        val user: String,
        @field:Filled
        val token: String
)
