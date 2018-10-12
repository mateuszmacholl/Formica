package mateuszmacholl.formica.validation.post

import mateuszmacholl.formica.service.post.PostService
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ExistPostWithIdValidator : ConstraintValidator<ExistPostWithId, Number> {
    @Autowired
    private lateinit var postService: PostService

    override fun initialize(constraint: ExistPostWithId) {}

    override fun isValid(id: Number, context: ConstraintValidatorContext): Boolean {
        return postService.findById(id as Int).isPresent
    }

}
