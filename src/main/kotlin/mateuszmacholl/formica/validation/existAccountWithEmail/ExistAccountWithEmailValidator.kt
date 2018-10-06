package mateuszmacholl.formica.validation.existAccountWithEmail

import mateuszmacholl.formica.helper.EmailPattern
import mateuszmacholl.formica.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ExistAccountWithEmailValidator : ConstraintValidator<ExistAccountWithEmail, String> {

    @Autowired
    private lateinit var userService: UserService

    override fun initialize(constraint: ExistAccountWithEmail?) {}

    override fun isValid(email: String, context: ConstraintValidatorContext): Boolean {
        context.disableDefaultConstraintViolation()

        if (!EmailPattern.isCorrect(email)) {
            context.buildConstraintViolationWithTemplate("wrong pattern")
                    .addConstraintViolation()
            return false
        }
        if (userService.findByEmail(email) == null) {
            context.buildConstraintViolationWithTemplate("no account with such email")
                    .addConstraintViolation()
            return false
        }
        return true
    }

}
