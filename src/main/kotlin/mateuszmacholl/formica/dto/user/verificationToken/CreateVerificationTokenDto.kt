package mateuszmacholl.formica.dto.user.verificationToken

import mateuszmacholl.formica.validation.filled.Filled
import javax.validation.constraints.NotNull

class CreateVerificationTokenDto(
        @field:NotNull
        val user: Int? = null,
        @field:Filled
        val token: String? = null
)