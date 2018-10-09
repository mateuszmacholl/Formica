package mateuszmacholl.formica.validation.user.existUserWithUsername

import mateuszmacholl.formica.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ExistAccountWithUsernameValidator : ConstraintValidator<ExistAccountWithUsername, String> {

    @Autowired
    private lateinit var userService: UserService

    override fun initialize(constraint: ExistAccountWithUsername) {}

    override fun isValid(username: String?, context: ConstraintValidatorContext): Boolean {
        context.disableDefaultConstraintViolation()

        if (username == null || userService.findByUsername(username) == null) {
            context.buildConstraintViolationWithTemplate("no account with such username")
                    .addConstraintViolation()
            return false
        }
        return true
    }

}
