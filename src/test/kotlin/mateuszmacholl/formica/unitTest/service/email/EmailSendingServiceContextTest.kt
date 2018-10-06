package mateuszmacholl.formica.unitTest.service.email

import mateuszmacholl.formica.service.user.email.EmailSendingService
import mateuszmacholl.formica.service.user.email.EmailSendingServiceContext
import mateuszmacholl.formica.service.user.email.ResetPasswordEmailSendingService
import mateuszmacholl.formica.service.user.email.VerificationEmailSendingService
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.mail.MailSender
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class EmailSendingServiceContextTest {

    private var emailSendingServiceContext: EmailSendingServiceContext? = null

    @Mock
    lateinit var mailSender: MailSender

    @BeforeEach
    fun init() {
        emailSendingServiceContext = EmailSendingServiceContext()

        //GIVEN
        val emailSendingServices = ArrayList<EmailSendingService>()
        emailSendingServices.add(ResetPasswordEmailSendingService(mailSender))
        emailSendingServices.add(VerificationEmailSendingService(mailSender))
        emailSendingServiceContext!!.setEmailSendingServices(emailSendingServices)
    }

    @Test
    fun getEmailSendingService_returnCorrectEmailSendingService() {
        //WHEN
        val emailSendingService = emailSendingServiceContext!!
                .getEmailSendingService(ResetPasswordEmailSendingService::class.java)
        //GIVEN
        assertTrue(ResetPasswordEmailSendingService::class.java.isInstance(emailSendingService))
    }

    @Test
    fun getEmailSendingService_returnWrongEmailSendingService() {
        //WHEN
        val emailSendingService = emailSendingServiceContext!!
                .getEmailSendingService(ResetPasswordEmailSendingService::class.java)
        //GIVEN
        assertFalse(VerificationEmailSendingService::class.java.isInstance(emailSendingService))
    }

}
