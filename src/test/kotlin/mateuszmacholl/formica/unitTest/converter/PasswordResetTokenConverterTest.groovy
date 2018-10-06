package mateuszmacholl.formica.unitTest.converter

import mateuszmacholl.formica.converter.user.PasswordResetTokenConverter
import mateuszmacholl.formica.dto.user.passwordResetToken.CreatePasswordResetTokenDto
import mateuszmacholl.formica.model.user.User
import mateuszmacholl.formica.service.user.UserService
import spock.lang.Specification

class PasswordResetTokenConverterTest extends Specification {
    UserService userService = Mock(UserService)
    def passwordResetTokenConverter = new PasswordResetTokenConverter()
    final static userId = 1000
    final static token = 'token12345'
    final static createPasswordResetTokenDto = new CreatePasswordResetTokenDto(userId, token)
    final static username = 'username'
    final static User user = new User()

    def setup() {
        passwordResetTokenConverter.userService = userService
        user.id = userId
        user.username = username
    }

    def 'toEntity_returnCorrectPasswordResetToken'() {
        when:
        def passwordResetToken = passwordResetTokenConverter.toEntity(createPasswordResetTokenDto)
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
        passwordResetTokenConverter.toEntity(createPasswordResetTokenDto)
        then:
        thrown(IllegalArgumentException)

        userService.findById(userId) >> { return Optional.empty() }
    }
}
