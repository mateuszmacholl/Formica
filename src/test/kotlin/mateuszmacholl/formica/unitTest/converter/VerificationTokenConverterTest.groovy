package mateuszmacholl.formica.unitTest.converter

import mateuszmacholl.formica.converter.user.VerificationTokenConverter
import mateuszmacholl.formica.dto.user.verificationToken.CreateVerificationTokenDto
import mateuszmacholl.formica.model.user.User
import mateuszmacholl.formica.service.user.UserService
import spock.lang.Specification

class VerificationTokenConverterTest extends Specification {
    UserService userService = Mock(UserService)
    def verificationTokenConverter = new VerificationTokenConverter()
    final static userId = 1000
    final static token = 'token12345'
    final static createVerificationTokenDto = new CreateVerificationTokenDto(userId, token)
    final static username = 'username'
    final static User user = new User()

    def setup() {
        verificationTokenConverter.userService = userService
        user.id = userId
        user.username = username
    }

    def 'toEntity_returnCorrectPasswordResetToken'() {
        when:
        def passwordResetToken = verificationTokenConverter.toEntity(createVerificationTokenDto)
        then:
        passwordResetToken.user.username == user.username
        passwordResetToken.user.id == userId
        passwordResetToken.token == token

        userService.findById(userId) >> { return Optional.of(user) }
    }

    def 'toEntity_passWrongUserId_throwIllegalArgumentException'() {
        given:
        user.id = 1
        when:
        verificationTokenConverter.toEntity(createVerificationTokenDto)
        then:
        thrown(IllegalArgumentException)

        userService.findById(userId) >> { return Optional.empty() }
    }
}
