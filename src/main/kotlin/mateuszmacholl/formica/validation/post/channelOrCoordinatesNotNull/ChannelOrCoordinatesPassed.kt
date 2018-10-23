package mateuszmacholl.formica.validation.post.channelOrCoordinatesNotNull

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [ChannelOrCoordinatesPassedValidator::class])
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
annotation class ChannelOrCoordinatesPassed(
        val message: String = "Pass channel or coordinates",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = [],
        val channel: String, val coordinates: String)