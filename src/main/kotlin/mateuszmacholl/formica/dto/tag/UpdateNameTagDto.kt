package mateuszmacholl.formica.dto.tag

import mateuszmacholl.formica.validation.filled.Filled

data class UpdateNameTagDto(
        @field:Filled
        val name: String
)
