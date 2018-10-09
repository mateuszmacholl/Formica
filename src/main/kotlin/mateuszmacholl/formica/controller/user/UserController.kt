package mateuszmacholl.formica.controller.user

import mateuszmacholl.formica.converter.user.UserConverter
import mateuszmacholl.formica.dto.user.ChangePasswordDto
import mateuszmacholl.formica.dto.user.CreateUserDto
import mateuszmacholl.formica.service.token.PasswordResetTokenService
import mateuszmacholl.formica.service.token.VerificationTokenService
import mateuszmacholl.formica.service.url.UrlConstructorService
import mateuszmacholl.formica.service.user.UserService
import mateuszmacholl.formica.service.user.email.EmailSendingServiceContext
import mateuszmacholl.formica.service.user.email.ResetPasswordEmailSendingService
import mateuszmacholl.formica.service.user.email.VerificationEmailSendingService
import mateuszmacholl.formica.specification.UserSpec
import mateuszmacholl.formica.validation.token.passwordResetToken.correctPasswordResetToken.CorrectPasswordResetToken
import mateuszmacholl.formica.validation.token.verificationToken.correctVerificationToken.CorrectVerificationToken
import mateuszmacholl.formica.validation.user.existAccountWithEmail.ExistAccountWithEmail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

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
    lateinit var urlConstructorService: UrlConstructorService
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
            return ResponseEntity(user, HttpStatus.OK)
        }
    }

    @RequestMapping(value = [""], method = [RequestMethod.POST])
    fun add(@RequestBody @Validated createUserDto: CreateUserDto): ResponseEntity<*> {
        val user = userConverter.toEntity(createUserDto)
        userService.add(user)

        val verificationToken = verificationTokenService.generateToken(user)

        val clientUrl = urlConstructorService.constructWithToken(createUserDto.url, verificationToken.token!!)
        emailSendingServiceContext.getEmailSendingService(VerificationEmailSendingService::class.java)!!.sendEmail(user.email!!, clientUrl)
        return ResponseEntity<Any>(HttpStatus.CREATED)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    fun deleteById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val user = userService.findById(id)
        return if (!user.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            userService.delete(user.get())
            ResponseEntity<Any>(HttpStatus.NO_CONTENT)
        }
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

    @RequestMapping(value = ["/password-reset-tokens"], method = [RequestMethod.POST])
    fun resetPassword(@RequestParam(value = "email") @ExistAccountWithEmail email: String,
                      @RequestParam url: String): ResponseEntity<*> {

        val user = userService.findByEmail(email)

        val passwordResetToken = passwordResetTokenService.generateToken(user!!)

        val clientUrl = urlConstructorService.constructWithToken(url, passwordResetToken.token!!)
        emailSendingServiceContext.getEmailSendingService(ResetPasswordEmailSendingService::class.java)!!.sendEmail(user.email!!, clientUrl)
        return ResponseEntity<Any>("Password reset token has been sent in the written e-mail", HttpStatus.CREATED)
    }

    @RequestMapping(value = ["/{id}/password-reset-tokens"], method = [RequestMethod.GET])
    fun getPasswordResetToken(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val user = userService.findById(id)
        return if (!user.isPresent) {
            ResponseEntity<Any>(HttpStatus.NOT_FOUND)
        } else {
            val passwordResetToken = user.get().passwordResetToken
            ResponseEntity<Any>(passwordResetToken, HttpStatus.OK)
        }
    }

    @RequestMapping(value = ["/password"], method = [RequestMethod.PUT])
    fun changePassword(@RequestBody @Validated changePasswordDto: ChangePasswordDto,
                       @RequestParam(value = "token") @CorrectPasswordResetToken token: String): ResponseEntity<*> {
        val passwordResetToken = passwordResetTokenService.findByToken(token)

        val user = passwordResetToken!!.user

        userService.changePassword(user!!, changePasswordDto.newPassword)
        passwordResetTokenService.deleteByToken(token)
        return ResponseEntity<Any>(HttpStatus.NO_CONTENT)
    }

}