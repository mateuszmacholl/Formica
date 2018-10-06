package mateuszmacholl.formica.validation.filled

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class FilledValidator : ConstraintValidator<Filled, String> {
    override fun initialize(constraint: Filled) {}

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        context.disableDefaultConstraintViolation()

        if (value == null) {
            context.buildConstraintViolationWithTemplate("Can't be null")
                    .addConstraintViolation()
            return false
        }
        if (value.isEmpty()) {
            context.buildConstraintViolationWithTemplate("Can't be empty")
                    .addConstraintViolation()
            return false
        }
        return true
    }

}
