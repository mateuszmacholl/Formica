package mateuszmacholl.formica.service.user.email

import mateuszmacholl.formica.model.user.User
import mateuszmacholl.shoppinglistapi.dto.email.EmailDto
import org.springframework.mail.MailSender
import org.springframework.stereotype.Service

@Service
class VerificationEmailSendingService(mailSender: MailSender) : EmailSendingService(mailSender) {

    public override fun generateEmail(user: User, url: String): EmailDto {
        return EmailDto(user.email, "Verify your account on blog website", url)
    }
}
