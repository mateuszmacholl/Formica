package mateuszmacholl.formica.unitTest.service.email

import mateuszmacholl.formica.model.user.User
import mateuszmacholl.formica.service.user.email.VerificationEmailSendingService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.mail.MailSender

class VerificationEmailSendingServiceTest {
    private lateinit var verificationEmailSendingService: VerificationEmailSendingService
    private val user = User()
    private val url = "t_url"

    @BeforeEach
    fun init() {
        val mailSender = mock(MailSender::class.java)
        verificationEmailSendingService = VerificationEmailSendingService(mailSender)

        //GIVEN
        user.email = "t_email"
    }

    @Test
    fun generateEmail_returnsCorrectlyCreatedEmailDto() {

        //WHEN
        val emailDto = verificationEmailSendingService.generateEmail(user.email!!, url)
        //THEN
        assertEquals(emailDto.destinationEmail, user.email)
        assertEquals(emailDto.body, url)

    }
}
