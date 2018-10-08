package mateuszmacholl.formica.validation.user.uniqueUsername

import mateuszmacholl.formica.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class UniqueUsernameValidator : ConstraintValidator<UniqueUsername, String> {

    @Autowired
    lateinit var userService: UserService

    override fun initialize(constraint: UniqueUsername) {}

    override fun isValid(username: String?, context: ConstraintValidatorContext): Boolean {
        context.disableDefaultConstraintViolation()
        if (username == null || userService.findByUsername(username) != null) {
            context.buildConstraintViolationWithTemplate("must be unique")
                    .addConstraintViolation()
            return false
        }
        return true
    }
}

