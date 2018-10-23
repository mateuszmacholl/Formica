package mateuszmacholl.formica.converter.channel

import mateuszmacholl.formica.converter.DtoConverter
import mateuszmacholl.formica.dto.answer.CreateAnswerDto
import mateuszmacholl.formica.dto.channel.CreateChannelDto
import mateuszmacholl.formica.model.answer.Answer
import mateuszmacholl.formica.model.channel.Channel
import mateuszmacholl.formica.service.channel.ChannelService
import mateuszmacholl.formica.service.coordinates.CoordinatesService
import mateuszmacholl.formica.service.post.PostService
import mateuszmacholl.formica.service.user.UserService
import org.springframework.stereotype.Service

@Service
class ChannelConverter(val coordinatesService: CoordinatesService) : DtoConverter<CreateChannelDto>() {
    override fun toEntity(createChannelDto: CreateChannelDto): Channel {
        val coordinates = coordinatesService.add(createChannelDto.coordinates)
        return Channel(
                coordinates = coordinates,
                name = createChannelDto.name
        )
    }
}
