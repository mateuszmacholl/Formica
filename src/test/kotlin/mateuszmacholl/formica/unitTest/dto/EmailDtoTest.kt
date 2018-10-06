package mateuszmacholl.formica.unitTest.dto

import mateuszmacholl.formica.dto.user.email.EmailDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class EmailDtoTest {

    private var emailDto: EmailDto? = null

    @BeforeEach
    fun init() {
        emailDto = EmailDto(DESTINATION, SUBJECT, BODY)
    }

    @Test
    fun constructEmail_returnCorrectlyMappedSimpleMailMessage() {
        val simpleMailMessage = emailDto!!.constructEmail()
        assertEquals(simpleMailMessage.subject, emailDto!!.subject)
        assertEquals(simpleMailMessage.to!![0], emailDto!!.destinationEmail) // i send email to one person
        assertEquals(simpleMailMessage.text, emailDto!!.body)
    }

    companion object {
        private const val DESTINATION = "t_destinationEmail"
        private const val SUBJECT = "t_subject"
        private const val BODY = "t_body"
    }
}
