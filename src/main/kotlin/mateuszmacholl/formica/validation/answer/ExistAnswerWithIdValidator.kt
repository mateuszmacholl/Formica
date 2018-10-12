package mateuszmacholl.formica.validation.answer

import mateuszmacholl.formica.service.answer.AnswerService
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ExistAnswerWithIdValidator : ConstraintValidator<ExistAnswerWithId, Number> {
    @Autowired
    private lateinit var answerService: AnswerService

    override fun initialize(constraint: ExistAnswerWithId) {}

    override fun isValid(id: Number, context: ConstraintValidatorContext): Boolean {
        return answerService.findById(id as Int).isPresent
    }

}