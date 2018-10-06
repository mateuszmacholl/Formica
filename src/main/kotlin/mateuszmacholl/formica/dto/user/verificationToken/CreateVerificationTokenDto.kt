package mateuszmacholl.formica.dto.user.verificationToken

import mateuszmacholl.formica.validation.filled.Filled
import javax.validation.constraints.NotNull

class CreateVerificationTokenDto(
        @NotNull
        val user: Int? = null,
        @Filled
        val token:String? = null
)