package mateuszmacholl.formica.converter.token

import mateuszmacholl.formica.converter.DtoConverter
import mateuszmacholl.formica.dto.token.verificationToken.CreateVerificationTokenDto
import mateuszmacholl.formica.model.token.VerificationToken
import mateuszmacholl.formica.service.user.UserService
import org.springframework.stereotype.Service

@Service
class VerificationTokenConverter(val userService: UserService): DtoConverter<CreateVerificationTokenDto>() {
    override fun toEntity(createVerificationTokenDto: CreateVerificationTokenDto): VerificationToken {
        val user = userService.findByUsername(createVerificationTokenDto.user) ?: throw IllegalArgumentException()
        return VerificationToken(token = createVerificationTokenDto.token, user = user)
    }
}
