package mateuszmacholl.formica.validation.tag.existTagWithId

import mateuszmacholl.formica.service.tag.TagService
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ExistTagsWithNameValidator : ConstraintValidator<ExistTagsWithName, List<String>> {
    @Autowired
    private lateinit var tagService: TagService

    override fun initialize(constraint: ExistTagsWithName) {}

    override fun isValid(names: List<String>, context: ConstraintValidatorContext): Boolean {
        context.disableDefaultConstraintViolation()
        for (name in names){
            if(tagService.findByName(name) ==  null){
                context.buildConstraintViolationWithTemplate("There is no tag with such name: $name")
                        .addConstraintViolation()
                return false
            }
        }
        return true
    }

}