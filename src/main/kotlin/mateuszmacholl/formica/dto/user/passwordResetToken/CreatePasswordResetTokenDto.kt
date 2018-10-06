package mateuszmacholl.formica.dto.user.passwordResetToken

import mateuszmacholl.formica.validation.filled.Filled
import javax.validation.constraints.NotNull

class CreatePasswordResetTokenDto(
        @NotNull
        var user: Int? = null,
        @Filled
        var token: String? = null
)
