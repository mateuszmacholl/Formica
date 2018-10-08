package mateuszmacholl.formica.service.user.email

import mateuszmacholl.formica.dto.user.email.EmailDto
import org.springframework.mail.MailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
abstract class EmailSendingService protected constructor(private val mailSender: MailSender) {

    protected abstract fun generateEmail(email: String, url: String): EmailDto

    @Async
    fun sendEmail(email: String, url: String) {
        val emailDto = generateEmail(email, url)
        mailSender.send(emailDto.constructEmail())
    }
}
