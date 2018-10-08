package mateuszmacholl.formica.validation.user.uniqueEmail

import mateuszmacholl.formica.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class UniqueEmailValidator : ConstraintValidator<UniqueEmail, String> {

    @Autowired
    lateinit var userService: UserService

    override fun initialize(constraint: UniqueEmail) {}

    override fun isValid(email: String?, context: ConstraintValidatorContext): Boolean {
        context.disableDefaultConstraintViolation()
        if (email == null || userService.findByEmail(email) != null) {
            context.buildConstraintViolationWithTemplate("must be unique")
                    .addConstraintViolation()
            return false
        }
        return true
    }
}

