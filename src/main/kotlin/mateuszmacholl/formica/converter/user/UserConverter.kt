package mateuszmacholl.formica.converter.user

import mateuszmacholl.formica.dto.user.CreateUserDto
import mateuszmacholl.formica.model.user.User
import org.springframework.stereotype.Service

@Service
class UserConverter {
    fun toEntity(createUserDto: CreateUserDto) = User(
            username = createUserDto.username,
            email = createUserDto.email,
            password = createUserDto.password
    )
}