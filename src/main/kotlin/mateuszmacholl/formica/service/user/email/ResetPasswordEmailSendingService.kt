package mateuszmacholl.formica.service.user.email

import mateuszmacholl.formica.dto.user.email.EmailDto
import org.springframework.mail.MailSender
import org.springframework.stereotype.Service

@Service
class ResetPasswordEmailSendingService(mailSender: MailSender) : EmailSendingService(mailSender) {

    public override fun generateEmail(email: String, url: String): EmailDto {
        return EmailDto(email, "Reset your password on blog website", url)
    }
}
