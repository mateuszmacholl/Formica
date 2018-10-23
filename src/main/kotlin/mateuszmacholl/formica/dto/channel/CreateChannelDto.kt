package mateuszmacholl.formica.dto.channel

import mateuszmacholl.formica.model.coordinates.Coordinates
import mateuszmacholl.formica.validation.filled.Filled
import javax.validation.constraints.NotNull

data class CreateChannelDto(
        @field:Filled
        val name: String,
        @field:NotNull
        val coordinates: Coordinates
)
