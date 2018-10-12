package mateuszmacholl.formica.validation.tag

import mateuszmacholl.formica.service.tag.TagService
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class UniqueTagNameValidator : ConstraintValidator<UniqueTagName, String> {

    @Autowired
    lateinit var tagService: TagService

    override fun initialize(constraint: UniqueTagName) {}

    override fun isValid(name: String, context: ConstraintValidatorContext): Boolean {
        context.disableDefaultConstraintViolation()
        if (tagService.findByName(name) != null) {
            context.buildConstraintViolationWithTemplate("must be unique")
                    .addConstraintViolation()
            return false
        }
        return true
    }
}

