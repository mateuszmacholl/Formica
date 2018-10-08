package mateuszmacholl.formica.validation.filled

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class FilledValidator : ConstraintValidator<Filled, String> {
    override fun initialize(constraint: Filled) {}

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        context.disableDefaultConstraintViolation()

        if (value.isNullOrBlank()) {
            context.buildConstraintViolationWithTemplate("Can't be null or blank")
                    .addConstraintViolation()
            return false
        }
        return true
    }

}
