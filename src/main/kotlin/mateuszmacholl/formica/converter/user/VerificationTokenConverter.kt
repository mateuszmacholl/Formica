package mateuszmacholl.formica.converter.user

import mateuszmacholl.formica.dto.user.verificationToken.CreateVerificationTokenDto
import mateuszmacholl.formica.model.user.VerificationToken
import mateuszmacholl.formica.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VerificationTokenConverter {
    @Autowired
    lateinit var userService: UserService

    fun toEntity(createVerificationTokenDto: CreateVerificationTokenDto): VerificationToken {
        val user = userService.findById(createVerificationTokenDto.user)
        if(!user.isPresent){
            throw IllegalArgumentException()
        }
        return VerificationToken(token = createVerificationTokenDto.token, user = user.get())
    }

}
