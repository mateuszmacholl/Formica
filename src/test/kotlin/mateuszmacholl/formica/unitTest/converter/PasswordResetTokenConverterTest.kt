package mateuszmacholl.formica.unitTest.converter

import mateuszmacholl.formica.converter.token.PasswordResetTokenConverter
import mateuszmacholl.formica.dto.token.passwordResetToken.CreatePasswordResetTokenDto
import mateuszmacholl.formica.model.user.User
import mateuszmacholl.formica.service.user.UserService
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock



class PasswordResetTokenConverterTest {
    private lateinit var userService: UserService
    private lateinit var passwordResetTokenConverter: PasswordResetTokenConverter
    private val token = "token12345"
    private val username = "username"
    private val createPasswordResetTokenDto = CreatePasswordResetTokenDto(user = username, token = token)
    private val user = User(username = username)

    @BeforeEach
    fun init() {
        userService = mock(UserService::class.java)
        passwordResetTokenConverter = PasswordResetTokenConverter()
        passwordResetTokenConverter.userService = userService
        `when`(passwordResetTokenConverter.userService.findByUsername(username)).thenReturn(user)
    }

    @Test
    fun toEntity_returnCorrectPasswordResetToken() {
        //when
        val passwordResetToken = passwordResetTokenConverter.toEntity(createPasswordResetTokenDto)
        //then
        assertTrue(passwordResetToken.user!!.username === user.username)
        assertTrue(passwordResetToken.user!!.username == username)
        assertTrue(passwordResetToken.token === token)
    }

    @Test
    fun toEntity_throwIllegalArgumentException(){
        //given
        val wrongCreatePasswordResetTokenDto = createPasswordResetTokenDto.copy(user = "wrongUsername")
        //when
        assertThrows<IllegalArgumentException> { passwordResetTokenConverter.toEntity(wrongCreatePasswordResetTokenDto) }
    }
}