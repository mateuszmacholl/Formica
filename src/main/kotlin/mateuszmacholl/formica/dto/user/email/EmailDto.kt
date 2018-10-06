package mateuszmacholl.formica.dto.user.email

import org.springframework.mail.SimpleMailMessage

class EmailDto(var destinationEmail: String?, var subject: String?, var body: String?) {

    fun constructEmail(): SimpleMailMessage {
        val email = SimpleMailMessage()
        email.setSubject(subject!!)
        email.setText(body!!)
        email.setTo(destinationEmail!!)
        return email
    }
}
