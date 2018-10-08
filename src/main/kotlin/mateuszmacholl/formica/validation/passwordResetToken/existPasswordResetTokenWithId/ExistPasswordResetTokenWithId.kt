package mateuszmacholl.formica.validation.passwordResetToken.existPasswordResetTokenWithId

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ExistPasswordResetTokenWithIdValidator::class])
annotation class ExistPasswordResetTokenWithId(
        val message: String = "There is no token with such id",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)