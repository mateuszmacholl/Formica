package mateuszmacholl.formica.validation.user.existAccountWithId

import mateuszmacholl.formica.service.token.PasswordResetTokenService
import mateuszmacholl.formica.validation.token.verificationToken.existVerificationTokenWithId.ExistVerificationTokenWithId
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

abstract class ExistAccountWithIdValidator : ConstraintValidator<ExistVerificationTokenWithId, Int> {
    @Autowired
    private lateinit var passwordResetTokenService: PasswordResetTokenService

    override fun initialize(constraint: ExistVerificationTokenWithId) {}

    override fun isValid(id: Int?, context: ConstraintValidatorContext): Boolean {
        return id != null && passwordResetTokenService.findById(id).isPresent
    }

}
