package mateuszmacholl.formica.converter.channel

import mateuszmacholl.formica.converter.DtoConverter
import mateuszmacholl.formica.dto.channel.CreateChannelDto
import mateuszmacholl.formica.model.channel.Channel
import mateuszmacholl.formica.service.coordinates.CoordinatesService
import org.springframework.stereotype.Service

@Service
class ChannelConverter(val coordinatesService: CoordinatesService) : DtoConverter<CreateChannelDto>() {
    override fun toEntity(from: CreateChannelDto): Channel {
        val coordinates = coordinatesService.add(from.coordinates)
        return Channel(
                coordinates = coordinates,
                name = from.name
        )
    }
}
