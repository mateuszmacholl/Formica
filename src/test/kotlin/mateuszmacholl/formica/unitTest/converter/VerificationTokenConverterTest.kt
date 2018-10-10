package mateuszmacholl.formica.unitTest.converter

import mateuszmacholl.formica.converter.token.VerificationTokenConverter
import mateuszmacholl.formica.dto.token.verificationToken.CreateVerificationTokenDto
import mateuszmacholl.formica.model.user.User
import mateuszmacholl.formica.service.user.UserService
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock



class VerificationTokenConverterTest {
    private lateinit var userService: UserService
    private lateinit var verificationTokenConverter: VerificationTokenConverter
    private val token = "token12345"
    private val username = "username"
    private val createVerificationTokenDto = CreateVerificationTokenDto(user = username, token = token)
    private val user = User(username = username)

    @BeforeEach
    fun init() {
        userService = mock(UserService::class.java)
        verificationTokenConverter = VerificationTokenConverter(userService)
        `when`(verificationTokenConverter.userService.findByUsername(username)).thenReturn(user)
    }

    @Test
    fun toEntity_returnCorrectPasswordResetToken() {
        //when
        val passwordResetToken = verificationTokenConverter.toEntity(createVerificationTokenDto)
        //then
        assertTrue(passwordResetToken.user!!.username === user.username)
        assertTrue(passwordResetToken.user!!.username == username)
        assertTrue(passwordResetToken.token === token)
    }

    @Test
    fun toEntity_throwIllegalArgumentException(){
        //given
        val wrongCreateVerificationTokenDto = createVerificationTokenDto.copy(user = "wrongUsername")

        //when
        assertThrows<IllegalArgumentException> { verificationTokenConverter.toEntity(wrongCreateVerificationTokenDto) }
    }


}