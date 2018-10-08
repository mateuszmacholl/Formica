package mateuszmacholl.formica.validation.verificationToken.existVerificationTokenWithId

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ExistVerificationTokenWithIdValidator::class])
annotation class ExistVerificationTokenWithId(
        val message: String = "There is no token with such id",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)