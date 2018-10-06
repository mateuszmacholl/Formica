package mateuszmacholl.formica.dto.user.verificationToken

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class CreateVerificationTokenDto(
        @NotNull
        val user: Int? = null,
        @NotEmpty
        @NotNull
        val token:String? = null
)