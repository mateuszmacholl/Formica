package mateuszmacholl.formica.converter.user

import mateuszmacholl.formica.converter.DtoConverter
import mateuszmacholl.formica.dto.user.CreateUserDto
import mateuszmacholl.formica.model.user.User
import org.springframework.stereotype.Service

@Service
class UserConverter: DtoConverter<CreateUserDto>() {
   override fun toEntity(from: CreateUserDto) = User(
            username = from.username,
            email = from.email,
            password = from.password
    )
}