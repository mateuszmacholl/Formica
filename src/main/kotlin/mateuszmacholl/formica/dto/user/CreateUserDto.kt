package mateuszmacholl.formica.dto.user

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class CreateUserDto {
    @NotEmpty
    @NotNull
    var username: String? = null

    @NotEmpty
    @NotNull
    var email: String? = null

    @NotEmpty
    @NotNull
    var password: String? = null

    @NotEmpty
    @NotNull
    var url: String? = null
}
