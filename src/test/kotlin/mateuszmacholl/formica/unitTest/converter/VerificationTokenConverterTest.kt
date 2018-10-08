package mateuszmacholl.formica.unitTest.converter

import mateuszmacholl.formica.converter.user.VerificationTokenConverter
import mateuszmacholl.formica.dto.user.verificationToken.CreateVerificationTokenDto
import mateuszmacholl.formica.model.user.User
import mateuszmacholl.formica.service.user.UserService
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.*



class VerificationTokenConverterTest {
    private lateinit var userService: UserService
    private lateinit var verificationTokenConverter: VerificationTokenConverter
    private val userId = 1000
    private val token = "token12345"
    private val createVerificationTokenDto = CreateVerificationTokenDto(userId, token)
    private val username = "username"
    private val user = User(username = username)

    @BeforeEach
    fun init() {
        user.id = userId
        userService = mock(UserService::class.java)
        verificationTokenConverter = VerificationTokenConverter()
        verificationTokenConverter.userService = userService
        `when`(verificationTokenConverter.userService.findById(userId)).thenReturn(Optional.of(user))
    }

    @Test
    fun toEntity_returnCorrectPasswordResetToken() {
        //when
        val passwordResetToken = verificationTokenConverter.toEntity(createVerificationTokenDto)
        //then
        assertTrue(passwordResetToken.user!!.username === user.username)
        assertTrue(passwordResetToken.user!!.id == userId)
        assertTrue(passwordResetToken.token === token)
    }

    @Test
    fun toEntity_throwIllegalArgumentException(){
        //given
        val wrongCreateVerificationTokenDto = createVerificationTokenDto.copy(user = 1)

        //when
        assertThrows<IllegalArgumentException> { verificationTokenConverter.toEntity(wrongCreateVerificationTokenDto) }
    }


}