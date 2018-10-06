package mateuszmacholl.formica.controller.user

import mateuszmacholl.formica.converter.user.UserConverter
import mateuszmacholl.formica.dto.user.ChangePasswordDto
import mateuszmacholl.formica.dto.user.CreateUserDto
import mateuszmacholl.formica.model.user.PasswordResetToken
import mateuszmacholl.formica.model.user.VerificationToken
import mateuszmacholl.formica.service.user.UserService
import mateuszmacholl.formica.service.user.email.EmailSendingServiceContext
import mateuszmacholl.formica.service.user.email.ResetPasswordEmailSendingService
import mateuszmacholl.formica.service.user.email.VerificationEmailSendingService
import mateuszmacholl.formica.service.user.token.PasswordResetTokenService
import mateuszmacholl.formica.service.user.token.UrlFromTokenCreatorService
import mateuszmacholl.formica.service.user.token.VerificationTokenService
import mateuszmacholl.formica.specification.UserSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@Validated
@RequestMapping(value = ["/users"])
class UserController {
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var passwordResetTokenService: PasswordResetTokenService
    @Autowired
    lateinit var verificationTokenService: VerificationTokenService
    @Autowired
    lateinit var emailSendingServiceContext: EmailSendingServiceContext
    @Autowired
    lateinit var urlFromTokenCreatorService: UrlFromTokenCreatorService
    @Autowired
    lateinit var userConverter: UserConverter


    @RequestMapping(value = [""], method = [RequestMethod.GET])
    fun getAllBy(userSpec: UserSpec, pageable: Pageable): ResponseEntity<*> {
        val users = userService.findAll(userSpec, pageable)
        return ResponseEntity(users, HttpStatus.OK)
    }

    @RequestMapping(value = ["/{id}"])
    fun getById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val user = userService.findById(id)
        return if (!user.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity(user, HttpStatus.OK)
        }
    }

    @RequestMapping(value = [""], method = [RequestMethod.POST])
    fun add(@RequestBody @Validated createUserDto: CreateUserDto): ResponseEntity<*> {
        val user = userConverter.toEntity(createUserDto)
        userService.add(user)

        val token = generateToken()
        val verificationToken = VerificationToken(token, user)
        verificationTokenService.add(verificationToken)

        val url = urlFromTokenCreatorService.create(createUserDto.url, token)
        emailSendingServiceContext.getEmailSendingService(VerificationEmailSendingService::class.java)!!.sendEmail(user, url)
        return ResponseEntity<Any>(HttpStatus.CREATED)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    fun deleteById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val user = userService.findById(id)
        return if (!user.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            userService.delete(user.get())
            return ResponseEntity<Any>(HttpStatus.NO_CONTENT)
        }
    }

    private fun generateToken(): String {
        return UUID.randomUUID().toString()
    }

    @RequestMapping(value = ["/enabled"], method = [RequestMethod.PUT])
    fun verifyRegistration(@RequestParam(value = "token") token: String): ResponseEntity<*> {

        val verificationToken = verificationTokenService.getToken(token)
                ?: return ResponseEntity.badRequest().body("Not found verification token with content: $token")
        val user = verificationToken.user
                ?: return ResponseEntity.badRequest().body("Not found user with such verification token: $token")
        userService.enableUser(user)
        verificationTokenService.deleteByToken(token)
        return ResponseEntity<Any>(HttpStatus.NO_CONTENT)
    }

    @RequestMapping(value = ["/verification-token"], method = [RequestMethod.POST])
    fun resendVerificationToken(@RequestParam(value = "email") email: String,
                                @RequestParam clientUrl: String): ResponseEntity<*> {

        val user = userService.findByEmail(email)
                ?: return ResponseEntity.badRequest().body("Not found user with email: $email")

        val token = generateToken()
        val verificationToken = VerificationToken(token, user)
        verificationTokenService.add(verificationToken)

        val url = urlFromTokenCreatorService.create(clientUrl, token)
        emailSendingServiceContext.getEmailSendingService(VerificationEmailSendingService::class.java)!!.sendEmail(user, url)
        return ResponseEntity<Any>("Verification token was sent in the written e-mail", HttpStatus.CREATED)
    }

    @RequestMapping(value = ["/password-reset-token"], method = [RequestMethod.POST])
    fun resetPassword(@RequestParam(value = "email") email: String,
                      @RequestParam clientUrl: String): ResponseEntity<*> {

        val user = userService.findByEmail(email)
                ?: return ResponseEntity.badRequest().body("Not found user with email: $email")
        val token = generateToken()
        val passwordResetToken = PasswordResetToken(token, user)
        passwordResetTokenService.add(passwordResetToken)

        val url = urlFromTokenCreatorService.create(clientUrl, token)
        emailSendingServiceContext.getEmailSendingService(ResetPasswordEmailSendingService::class.java)!!.sendEmail(user, url)
        return ResponseEntity<Any>("Password reset token was sent in the written e-mail", HttpStatus.CREATED)
    }

    @RequestMapping(value = ["/password"], method = [RequestMethod.PUT])
    fun changePassword(@RequestBody @Validated changePasswordDto: ChangePasswordDto,
                       @RequestParam(value = "token") token: String): ResponseEntity<*> {
        val passwordResetToken = passwordResetTokenService.findByToken(token)
                ?: return ResponseEntity.badRequest().body("Not found password reset token with content: $token")

        val user = passwordResetToken.user

        userService.changePassword(user!!, changePasswordDto.newPassword!!)
        passwordResetTokenService.deleteByToken(token)
        return ResponseEntity.ok().build<Any>()
    }

}