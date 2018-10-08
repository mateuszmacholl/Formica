package mateuszmacholl.formica.dto.user.verificationToken

import mateuszmacholl.formica.validation.filled.Filled
import javax.validation.constraints.NotNull

data class CreateVerificationTokenDto(
        @field:NotNull
        val user: Int,
        @field:Filled
        val token: String
)