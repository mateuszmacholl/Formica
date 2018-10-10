package mateuszmacholl.formica.converter.token

import mateuszmacholl.formica.converter.DtoConverter
import mateuszmacholl.formica.dto.token.passwordResetToken.CreatePasswordResetTokenDto
import mateuszmacholl.formica.model.token.PasswordResetToken
import mateuszmacholl.formica.service.user.UserService
import org.springframework.stereotype.Service

@Service
class PasswordResetTokenConverter(val userService: UserService) : DtoConverter<CreatePasswordResetTokenDto>() {
    override fun toEntity(createPasswordResetTokenDto: CreatePasswordResetTokenDto): PasswordResetToken {
        val user = userService.findByUsername(createPasswordResetTokenDto.user) ?: throw IllegalArgumentException()
        return PasswordResetToken(token = createPasswordResetTokenDto.token, user = user)
    }
}
