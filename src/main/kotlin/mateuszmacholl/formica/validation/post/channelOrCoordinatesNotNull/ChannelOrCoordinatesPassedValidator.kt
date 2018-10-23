package mateuszmacholl.formica.validation.post.channelOrCoordinatesNotNull

import org.springframework.beans.BeanWrapperImpl
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ChannelOrCoordinatesPassedValidator : ConstraintValidator<ChannelOrCoordinatesPassed, Any> {
    private var channel: String? = null
    private var coordinates: String? = null

    override fun initialize(constraintAnnotation: ChannelOrCoordinatesPassed) {
        this.channel = constraintAnnotation.channel
        this.coordinates = constraintAnnotation.coordinates
    }

    override fun isValid(value: Any,
                         context: ConstraintValidatorContext): Boolean {

        val channelValue = BeanWrapperImpl(value).getPropertyValue(channel!!)
        val coordinatesValue = BeanWrapperImpl(value).getPropertyValue(coordinates!!)
        return if(channelValue != null && coordinatesValue == null){
            true
        } else channelValue == null && coordinatesValue != null
    }
}