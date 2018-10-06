package mateuszmacholl.formica.service.user.email

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EmailSendingServiceContext {
    private var emailSendingServices: MutableList<EmailSendingService> = mutableListOf()

    fun getEmailSendingService(emailSendingServiceClass: Class<*>): EmailSendingService? {
        val emailSendingService = emailSendingServices.stream().filter { emailSendingServiceClass.isInstance(it) }.findFirst()
        return emailSendingService.orElse(null)
    }

    @Autowired
    fun setEmailSendingServices(emailSendingServices: MutableList<EmailSendingService>) {
        this.emailSendingServices = emailSendingServices
    }
}
