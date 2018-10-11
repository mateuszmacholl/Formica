package mateuszmacholl.formica.validation.token.verificationToken.correctVerificationToken

import mateuszmacholl.formica.service.token.VerificationTokenService
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class CorrectVerificationTokenValidator : ConstraintValidator<CorrectVerificationToken, String> {

    @Autowired
    private val verificationTokenService: VerificationTokenService? = null

    override fun initialize(constraint: CorrectVerificationToken) {}

    override fun isValid(token: String, context: ConstraintValidatorContext): Boolean {
        context.disableDefaultConstraintViolation()
        val verificationToken = verificationTokenService!!.findByToken(token)

        if (verificationToken == null) {
            context.buildConstraintViolationWithTemplate("not found such token")
                    .addConstraintViolation()
            return false
        }
        if (verificationToken.hasExpired()) {
            context.buildConstraintViolationWithTemplate("has expired")
                    .addConstraintViolation()
            return false
        }
        if (verificationToken.user!!.enabled) {
            context.buildConstraintViolationWithTemplate("already enabled")
                    .addConstraintViolation()
            return false
        }
        return true
    }

}
