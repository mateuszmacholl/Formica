package mateuszmacholl.formica.controller.user

import mateuszmacholl.formica.converter.user.UserConverter
import mateuszmacholl.formica.dto.user.CreateUserDto
import mateuszmacholl.formica.service.token.VerificationTokenService
import mateuszmacholl.formica.service.url.UrlConstructorService
import mateuszmacholl.formica.service.user.UserService
import mateuszmacholl.formica.service.user.email.EmailSendingServiceContext
import mateuszmacholl.formica.service.user.email.VerificationEmailSendingService
import mateuszmacholl.formica.validation.token.verificationToken.correctVerificationToken.CorrectVerificationToken
import mateuszmacholl.formica.validation.user.existAccountWithEmail.ExistAccountWithEmail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping(value = ["/users"])
class RegistrationController {
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var userConverter: UserConverter
    @Autowired
    lateinit var verificationTokenService: VerificationTokenService
    @Autowired
    lateinit var emailSendingServiceContext: EmailSendingServiceContext
    @Autowired
    lateinit var urlConstructorService: UrlConstructorService

    @RequestMapping(value = [""], method = [RequestMethod.POST])
    fun add(@RequestBody @Validated createUserDto: CreateUserDto): ResponseEntity<*> {
        val user = userConverter.toEntity(createUserDto)
        userService.add(user)

        val verificationToken = verificationTokenService.generateToken(user)

        val clientUrl = urlConstructorService.constructWithToken(createUserDto.url, verificationToken.token!!)
        emailSendingServiceContext.getEmailSendingService(VerificationEmailSendingService::class.java)!!.sendEmail(user.email!!, clientUrl)
        return ResponseEntity<Any>(HttpStatus.CREATED)
    }

    @RequestMapping(value = ["/enabled"], method = [RequestMethod.PUT])
    fun verifyRegistration(@RequestParam(value = "token") @CorrectVerificationToken token: String): ResponseEntity<*> {
        val verificationToken = verificationTokenService.findByToken(token)
        val user = verificationToken!!.user
        userService.enableUser(user!!)
        verificationTokenService.deleteByToken(token)
        return ResponseEntity<Any>(HttpStatus.NO_CONTENT)
    }

    @RequestMapping(value = ["/{id}/verification-tokens"], method = [RequestMethod.GET])
    fun getVerificationToken(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val user = userService.findById(id)
        return if (!user.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            val verificationToken = user.get().verificationToken
            ResponseEntity<Any>(verificationToken, HttpStatus.OK)
        }
    }

    @RequestMapping(value = ["/verification-tokens"], method = [RequestMethod.POST])
    fun resendVerificationToken(@RequestParam(value = "email") @ExistAccountWithEmail email: String,
                                @RequestParam url: String): ResponseEntity<*> {
        val user = userService.findByEmail(email)
        if (user!!.enabled) {
            return ResponseEntity<Any>("account is already enabled", HttpStatus.BAD_REQUEST)
        }

        val verificationToken = verificationTokenService.generateToken(user)

        val clientUrl = urlConstructorService.constructWithToken(url, verificationToken.token!!)
        emailSendingServiceContext.getEmailSendingService(VerificationEmailSendingService::class.java)!!.sendEmail(user.email!!, clientUrl)
        return ResponseEntity<Any>("Verification token has been sent in the written e-mail", HttpStatus.CREATED)
    }
}