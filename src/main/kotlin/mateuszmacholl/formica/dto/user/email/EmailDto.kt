package mateuszmacholl.shoppinglistapi.dto.email

import org.springframework.mail.SimpleMailMessage

class EmailDto {
     var destinationEmail: String? = null
     var subject: String? = null
     var body: String? = null

    constructor(destinationEmail: String?, subject: String?, body: String?) {
        this.destinationEmail = destinationEmail
        this.subject = subject
        this.body = body
    }

    fun constructEmail(): SimpleMailMessage {
        val email = SimpleMailMessage()
        email.setSubject(subject!!)
        email.setText(body!!)
        email.setTo(destinationEmail!!)
        return email
    }
}
