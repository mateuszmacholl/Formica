package mateuszmacholl.formica.controller.user

import mateuszmacholl.formica.dto.user.ChangePasswordDto
import mateuszmacholl.formica.service.token.PasswordResetTokenService
import mateuszmacholl.formica.service.url.UrlConstructorService
import mateuszmacholl.formica.service.user.UserService
import mateuszmacholl.formica.service.user.email.EmailSendingServiceContext
import mateuszmacholl.formica.service.user.email.ResetPasswordEmailSendingService
import mateuszmacholl.formica.validation.token.passwordResetToken.correctPasswordResetToken.CorrectPasswordResetToken
import mateuszmacholl.formica.validation.user.existAccountWithEmail.ExistAccountWithEmail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping(value = ["/users"])
class LoginController {
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var passwordResetTokenService: PasswordResetTokenService
    @Autowired
    lateinit var emailSendingServiceContext: EmailSendingServiceContext
    @Autowired
    lateinit var urlConstructorService: UrlConstructorService

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