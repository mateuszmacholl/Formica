package mateuszmacholl.formica.unitTest.dto

import mateuszmacholl.formica.dto.user.email.EmailDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class EmailDtoTest {

    private lateinit var emailDto: EmailDto
    private val destination = "t_destinationEmail"
    private val subject = "t_subject"
    private val body = "t_body"

    @BeforeEach
    fun init() {
        emailDto = EmailDto(destination, subject, body)
    }

    @Test
    fun constructEmail_returnCorrectlyMappedSimpleMailMessage() {
        val simpleMailMessage = emailDto.constructEmail()
        assertEquals(simpleMailMessage.subject, emailDto.subject)
        assertEquals(simpleMailMessage.to!![0], emailDto.destinationEmail) // to one person
        assertEquals(simpleMailMessage.text, emailDto.body)
    }
}
