package mateuszmacholl.formica.validation.accountPasswordReset

import mateuszmacholl.formica.service.user.token.PasswordResetTokenService
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class CorrectPasswordResetTokenValidator : ConstraintValidator<CorrectPasswordResetToken, String> {

    @Autowired
    private val passwordResetTokenService: PasswordResetTokenService? = null

    override fun initialize(constraint: CorrectPasswordResetToken) {}

    override fun isValid(token: String?, context: ConstraintValidatorContext): Boolean {
        context.disableDefaultConstraintViolation()
        if(token == null) {
            return false
        }
        val passwordResetToken = passwordResetTokenService!!.findByToken(token)

        if (passwordResetToken == null) {
            context.buildConstraintViolationWithTemplate("not found such token")
                    .addConstraintViolation()
            return false
        }
        if (passwordResetToken.hasExpired()) {
            context.buildConstraintViolationWithTemplate("has expired")
                    .addConstraintViolation()
            return false
        }
        if (passwordResetToken.user!!.enabled!!) {
            context.buildConstraintViolationWithTemplate("already enabled")
                    .addConstraintViolation()
            return false
        }
        return true
    }

}