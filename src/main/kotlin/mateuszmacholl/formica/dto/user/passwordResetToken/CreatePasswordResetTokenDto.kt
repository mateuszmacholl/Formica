package mateuszmacholl.formica.dto.user.passwordResetToken

import mateuszmacholl.formica.validation.filled.Filled
import javax.validation.constraints.NotNull

class CreatePasswordResetTokenDto(
        @field:NotNull
        val user: Int? = null,
        @field:Filled
        val token: String? = null
)
