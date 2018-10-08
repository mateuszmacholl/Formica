package mateuszmacholl.formica.unitTest.converter

import mateuszmacholl.formica.converter.user.UserConverter
import mateuszmacholl.formica.dto.user.CreateUserDto
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserConverterTest {
    private lateinit var userConverter: UserConverter
    private val username: String = "username"
    private val email: String = "email"
    private val password: String = "password"
    private val url: String = "url"
    private val createUserDto = CreateUserDto(
            username = username,
            email = email,
            password = password,
            url = url
    )

    @BeforeEach
    fun init() {
        userConverter = UserConverter()
    }

    @Test
    fun toEntity_returnCorrectUser() {
        //when
        val user = userConverter.toEntity(createUserDto)
        //then
        assertTrue(user.username === "username")
        assertTrue(user.email === "email")
        assertTrue(user.password === "password")
    }
}