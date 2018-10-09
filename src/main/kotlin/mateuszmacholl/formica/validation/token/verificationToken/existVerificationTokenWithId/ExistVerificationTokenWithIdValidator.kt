package mateuszmacholl.formica.validation.token.verificationToken.existVerificationTokenWithId

import mateuszmacholl.formica.service.token.VerificationTokenService
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ExistVerificationTokenWithIdValidator : ConstraintValidator<ExistVerificationTokenWithId, Int> {

    @Autowired
    private lateinit var verificationTokenService: VerificationTokenService

    override fun initialize(constraint: ExistVerificationTokenWithId) {}

    override fun isValid(id: Int?, context: ConstraintValidatorContext): Boolean {
        return id != null && verificationTokenService.findById(id).isPresent
    }

}
