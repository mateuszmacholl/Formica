package mateuszmacholl.formica.validation.channel

import mateuszmacholl.formica.service.channel.ChannelService
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ExistChannelWithIdValidator : ConstraintValidator<ExistChannelWithId, Number> {
    @Autowired
    private lateinit var channelService: ChannelService

    override fun initialize(constraint: ExistChannelWithId) {}

    override fun isValid(id: Number?, context: ConstraintValidatorContext): Boolean {
        if(id == null){
            return true
        }
        return channelService.findById(id as Int).isPresent
    }

}
