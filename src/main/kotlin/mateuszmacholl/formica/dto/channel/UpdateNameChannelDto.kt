package mateuszmacholl.formica.dto.channel

import mateuszmacholl.formica.validation.filled.Filled

data class UpdateNameChannelDto(
        @field:Filled
        val name: String
)
