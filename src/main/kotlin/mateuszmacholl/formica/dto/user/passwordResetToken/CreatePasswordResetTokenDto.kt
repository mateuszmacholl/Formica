package mateuszmacholl.formica.dto.user.passwordResetToken

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class CreatePasswordResetTokenDto(
        @NotNull
        var user: Int? = null,
        @NotEmpty
        @NotNull
        var token: String? = null
)
