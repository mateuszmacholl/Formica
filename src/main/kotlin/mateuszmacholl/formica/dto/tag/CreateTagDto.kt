package mateuszmacholl.formica.dto.tag

import mateuszmacholl.formica.validation.filled.Filled
import mateuszmacholl.formica.validation.tag.uniqueTagName.UniqueTagName

data class CreateTagDto (
        @field:Filled
        @field:UniqueTagName
        val name: String
)