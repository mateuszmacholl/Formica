package mateuszmacholl.formica.service.user.email

import mateuszmacholl.formica.model.user.User
import mateuszmacholl.formica.dto.user.email.EmailDto
import org.springframework.mail.MailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
abstract class EmailSendingService protected constructor(private val mailSender: MailSender) {

    protected abstract fun generateEmail(user: User, url: String): EmailDto

    @Async
    fun sendEmail(user: User, url: String) {
        val emailDto = generateEmail(user, url)
        mailSender.send(emailDto.constructEmail())
    }
}
