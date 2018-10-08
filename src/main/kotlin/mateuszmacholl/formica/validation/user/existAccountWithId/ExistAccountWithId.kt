package mateuszmacholl.formica.validation.user.existAccountWithId

import mateuszmacholl.formica.validation.verificationToken.existVerificationTokenWithId.ExistVerificationTokenWithIdValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ExistVerificationTokenWithIdValidator::class])
annotation class ExistAccountWithId(
        val message: String = "There is no account with such id",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)