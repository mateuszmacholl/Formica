package mateuszmacholl.formica.dto.user

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class ChangePasswordDto {
    @NotNull
    @NotEmpty
    val newPassword: String? = null
}
