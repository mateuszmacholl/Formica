package mateuszmacholl.formica.validation.token.passwordResetToken.existPasswordResetTokenWithId

import mateuszmacholl.formica.service.token.PasswordResetTokenService
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ExistPasswordResetTokenWithIdValidator : ConstraintValidator<ExistPasswordResetTokenWithId, Int> {

    @Autowired
    private lateinit var passwordResetTokenService: PasswordResetTokenService

    override fun initialize(constraint: ExistPasswordResetTokenWithId) {}

    override fun isValid(id: Int, context: ConstraintValidatorContext): Boolean {
        return passwordResetTokenService.findById(id).isPresent
    }

}
