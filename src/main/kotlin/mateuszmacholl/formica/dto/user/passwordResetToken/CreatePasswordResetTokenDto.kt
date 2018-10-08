package mateuszmacholl.formica.dto.user.passwordResetToken

import mateuszmacholl.formica.validation.filled.Filled
import javax.validation.constraints.NotNull

data class CreatePasswordResetTokenDto(
        @field:NotNull
        val user: Int,
        @field:Filled
        val token: String
)
