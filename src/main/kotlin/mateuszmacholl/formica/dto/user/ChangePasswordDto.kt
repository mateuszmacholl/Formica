package mateuszmacholl.formica.dto.user

import mateuszmacholl.formica.validation.filled.Filled

data class ChangePasswordDto(
    @field:Filled
    val newPassword: String
)
