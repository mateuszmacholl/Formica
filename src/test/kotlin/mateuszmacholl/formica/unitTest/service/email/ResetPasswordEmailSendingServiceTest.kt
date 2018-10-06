package mateuszmacholl.formica.unitTest.service.email

import mateuszmacholl.formica.model.user.User
import mateuszmacholl.formica.service.user.email.ResetPasswordEmailSendingService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.mail.MailSender

internal class ResetPasswordEmailSendingServiceTest {

    private var resetPasswordEmailSendingService: ResetPasswordEmailSendingService? = null

    @BeforeEach
    fun init() {
        val mailSender = mock(MailSender::class.java)
        resetPasswordEmailSendingService = ResetPasswordEmailSendingService(mailSender)

        //GIVEN
        USER.email = "t_email"
    }

    @Test
    fun generateEmail_returnsCorrectlyCreatedEmailDto() {

        //WHEN
        val emailDto = resetPasswordEmailSendingService!!.generateEmail(USER, URL)
        //THEN
        assertEquals(emailDto.destinationEmail, USER.email)
        assertEquals(emailDto.body, URL)

    }

    companion object {

        private val USER = User()
        private const val URL = "t_url"
    }
}
