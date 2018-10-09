package mateuszmacholl.formica.dto.tag

import mateuszmacholl.formica.validation.filled.Filled
import mateuszmacholl.formica.validation.tag.UniqueTagName

data class CreateTagDto (
        @field:Filled
        @field:UniqueTagName
        val name: String
)