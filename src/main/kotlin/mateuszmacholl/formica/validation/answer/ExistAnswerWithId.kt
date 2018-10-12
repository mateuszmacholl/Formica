package mateuszmacholl.formica.validation.answer

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ExistAnswerWithIdValidator::class])
annotation class ExistAnswerWithId(
        val message: String = "There is no answer with such id",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)