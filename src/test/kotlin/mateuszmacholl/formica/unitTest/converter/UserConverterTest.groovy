package mateuszmacholl.formica.unitTest.converter

import mateuszmacholl.formica.converter.user.UserConverter
import mateuszmacholl.formica.dto.user.CreateUserDto
import spock.lang.Specification

class UserConverterTest extends Specification {
    private userConverter = new UserConverter()
    final static username = 'username'
    final static email = 'email'
    final static password = 'password'
    final static CreateUserDto createUserDto = new CreateUserDto()

    def setup(){
        given:
        createUserDto.username = username
        createUserDto.email = email
        createUserDto.password = password
    }

    def 'toEntity_returnCorrectUser'() {
        when:
        def user = userConverter.toEntity(createUserDto)
        then:
        user.username == 'username'
        user.email == 'email'
        user.password == 'password'
    }
}
