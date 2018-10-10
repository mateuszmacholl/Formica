package mateuszmacholl.formica.validation.user.existAccountWithId

import mateuszmacholl.formica.service.post.PostService
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ExistAccountWithIdValidator : ConstraintValidator<ExistAccountWithId, Int> {
    @Autowired
    private lateinit var postService: PostService

    override fun initialize(constraint: ExistAccountWithId) {}

    override fun isValid(id: Int?, context: ConstraintValidatorContext): Boolean {
        return id != null && postService.findById(id).isPresent
    }
}
