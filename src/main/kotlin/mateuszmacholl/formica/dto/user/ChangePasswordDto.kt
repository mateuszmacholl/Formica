package mateuszmacholl.formica.dto.user

import mateuszmacholl.formica.validation.filled.Filled

class ChangePasswordDto {
    @Filled
    val newPassword: String? = null
}
