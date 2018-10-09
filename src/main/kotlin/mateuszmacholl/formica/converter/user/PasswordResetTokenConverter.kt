package mateuszmacholl.formica.converter.user

import mateuszmacholl.formica.dto.user.passwordResetToken.CreatePasswordResetTokenDto
import mateuszmacholl.formica.model.token.PasswordResetToken
import mateuszmacholl.formica.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PasswordResetTokenConverter {
    @Autowired
    lateinit var userService: UserService

    fun toEntity(createPasswordResetTokenDto: CreatePasswordResetTokenDto): PasswordResetToken {
        val user = userService.findById(createPasswordResetTokenDto.user)
        if (!user.isPresent) {
            throw IllegalArgumentException()
        }
        return PasswordResetToken(token = createPasswordResetTokenDto.token, user = user.get())
    }

}
