package mateuszmacholl.formica.validation.post

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ExistPostWithIdValidator::class])
annotation class ExistPostWithId(
        val message: String = "There is no post with such id",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)