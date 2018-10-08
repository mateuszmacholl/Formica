package mateuszmacholl.formica.unitTest.converter

import mateuszmacholl.formica.converter.user.PasswordResetTokenConverter
import mateuszmacholl.formica.dto.user.passwordResetToken.CreatePasswordResetTokenDto
import mateuszmacholl.formica.model.user.User
import mateuszmacholl.formica.service.user.UserService
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.*



class PasswordResetTokenConverterTest {
    private lateinit var userService: UserService
    private lateinit var passwordResetTokenConverter: PasswordResetTokenConverter
    private val userId = 1000
    private val token = "token12345"
    private val createPasswordResetTokenDto = CreatePasswordResetTokenDto(userId, token)
    private val username = "username"
    private val user = User(username = username)

    @BeforeEach
    fun init() {
        user.id = userId
        userService = mock(UserService::class.java)
        passwordResetTokenConverter = PasswordResetTokenConverter()
        passwordResetTokenConverter.userService = userService
        `when`(passwordResetTokenConverter.userService.findById(userId)).thenReturn(Optional.of(user))
    }

    @Test
    fun toEntity_returnCorrectPasswordResetToken() {
        //when
        val passwordResetToken = passwordResetTokenConverter.toEntity(createPasswordResetTokenDto)
        //then
        assertTrue(passwordResetToken.user!!.username === user.username)
        assertTrue(passwordResetToken.user!!.id == userId)
        assertTrue(passwordResetToken.token === token)
    }

    @Test
    fun toEntity_throwIllegalArgumentException(){
        //given
        val wrongCreatePasswordResetTokenDto = createPasswordResetTokenDto.copy(user = 1)
        //when
        assertThrows<IllegalArgumentException> { passwordResetTokenConverter.toEntity(wrongCreatePasswordResetTokenDto) }
    }
}