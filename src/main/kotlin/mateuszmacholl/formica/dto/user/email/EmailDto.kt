package mateuszmacholl.formica.dto.user.email

import org.springframework.mail.SimpleMailMessage

data class EmailDto(val destinationEmail: String, val subject: String, val body: String) {
    fun constructEmail(): SimpleMailMessage {
        val email = SimpleMailMessage()
        email.setSubject(subject)
        email.setText(body)
        email.setTo(destinationEmail)
        return email
    }
}
