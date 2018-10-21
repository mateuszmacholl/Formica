package mateuszmacholl.formica.dto.post

import mateuszmacholl.formica.model.coordinates.Coordinates
import mateuszmacholl.formica.validation.channel.ExistChannelWithId
import mateuszmacholl.formica.validation.filled.Filled
import mateuszmacholl.formica.validation.post.channelOrCoordinatesNotNull.ChannelOrCoordinatesPassed
import mateuszmacholl.formica.validation.user.existUserWithUsername.ExistAccountWithUsername

@ChannelOrCoordinatesPassed(channel = "channel", coordinates = "coordinates")
data class CreatePostDto(
        @field:Filled
        val title: String,

        @field:Filled
        val content: String,

        @field:Filled
        @field:ExistAccountWithUsername
        val author: String,

        val coordinates: Coordinates? = null,
        @field:ExistChannelWithId
        val channel: Int? = null
)